package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import pl.mmorpg.prototype.clientservercommon.StatisticsCalculator;
import pl.mmorpg.prototype.clientservercommon.StatisticsOperations;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;
import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.exceptions.*;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.objects.items.Useable;
import pl.mmorpg.prototype.server.objects.monsters.abilities.Ability;
import pl.mmorpg.prototype.server.objects.monsters.abilities.TimedAbility;
import pl.mmorpg.prototype.server.path.search.BestFirstPathFinder;
import pl.mmorpg.prototype.server.path.search.PathFinder;
import pl.mmorpg.prototype.server.path.search.PathSimplifier;
import pl.mmorpg.prototype.server.path.search.collisionDetectors.RestrictedAreaGameObjectCollisionDetector;
import pl.mmorpg.prototype.server.path.search.distanceComparators.DistanceComparator;
import pl.mmorpg.prototype.server.path.search.distanceComparators.ManhattanDistanceComparator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Monster extends MovableGameObject implements ItemUser, EquipableItemsUser
{
	private static final float RELEASE_TARGET_DISTANCE = 400.0f;
	private static final BitmapFont font = Assets.getFont();
	private static final PathFinder pathFinder = new BestFirstPathFinder();

	// Only equipped items
	private final Map<EquipmentPosition, ItemWithEffectWrapper> equipmentPositionToEffectMap = new ConcurrentHashMap<>();
	// All items
	private final Map<Long, Item> items = new ConcurrentHashMap<>();
	private final List<Ability> abilities = new LinkedList<>();
	private final Map<Class<? extends Effect>, Effect> ongoingEffects = new ConcurrentHashMap<>();
	private LinkedList<Point> currentPath = new LinkedList<>();

	protected final MonsterProperties properties;
	// Actual statistics after modifications (by buffs, equipment, items etc)
	private Statistics actualStatistics;
	private Monster targetedMonster = null;
	private final RestrictedAreaGameObjectCollisionDetector collisionDetector;
	private float hitTime = 1000.0f;
	protected PlayState linkedState;

    private List<Monster> targetedBy = new LinkedList<>();

    public Monster(Texture lookout, long id, PlayState playState, MonsterProperties properties)
    {
        super(lookout, id, playState);
		linkedState = playState;
        this.properties = properties;
        this.actualStatistics = StatisticsCalculator.calculateStatistics(properties);
		collisionDetector = new RestrictedAreaGameObjectCollisionDetector(playState.getCollisionMap(), this);
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        targetedMonsterHandle(deltaTime);
        abilitiesUsageHandle();
        ongoingEffectsHandle(deltaTime);
		pathFollowingHandle(deltaTime);
	}

	private void targetedMonsterHandle(float deltaTime)
	{
		if (isTargetingAnotherMonster())
        {
			if(distance(getTargetedMonster()) >= RELEASE_TARGET_DISTANCE)
				stopTargetingMonster();
			else
				attackHandle(deltaTime);
        }
	}


	public boolean isTargetingAnotherMonster()
    {
        return targetedMonster != null;
    }

    private void attackHandle(float deltaTime)
    {
        hitTime += deltaTime;
        if (canAttackTarget())
        {
            hitTime = 0.0f;
            normalAttack(targetedMonster);
        }
    }

    private boolean canAttackTarget()
    {
        return hitTime >= actualStatistics.attackSpeed && distance(targetedMonster) <= actualStatistics.attackRange;
    }

    private double distance(Monster targetedMonster)
    {
        float selfCenterX = getX() + getWidth() / 2;
        float targetCenterX = targetedMonster.getX() + targetedMonster.getWidth() / 2;
        float deltaX = targetCenterX - selfCenterX;
        float selfCenterY = getY() + getHeight() / 2;
        float targetCenterY = targetedMonster.getY() + targetedMonster.getHeight() / 2;
        float deltaY = targetCenterY - selfCenterY;
		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    private void normalAttack(Monster target)
    {
        int damage = DamageCalculator.getDamage(this, target);
        target.properties.hp -= damage;
        linkedState.sendToAll(PacketsMaker.makeNormalDamagePacket(target.getId(), damage));
        if (target.properties.hp <= 0)
        {
            this.killed(target);
            target.die();
        }
    }


    private void abilitiesUsageHandle()
	{
		for(Ability ability : abilities)
		{
			if(ability instanceof TimedAbility)
				((TimedAbility)ability).updateWithDeltaTime(Gdx.graphics.getDeltaTime());
			if(ability.shouldUse())
			{
				if(ability.shouldBeUsedOnItself())
					ability.use(this, (PacketsSender)linkedState);
				else if(ability.shouldBeUsedOnTargetedMonster() && isTargetingAnotherMonster())
					ability.use(targetedMonster, (PacketsSender)linkedState);
			}
		}
	}


	private void ongoingEffectsHandle(float deltaTime)
	{
		ongoingEffects.forEach((effectType, effect) -> handleEffect(deltaTime, effectType, effect));
	}

	private void handleEffect(float deltaTime, Class<? extends Effect> effectType, Effect effect)
	{
		effect.update(deltaTime);
		if(effect.shouldDeactivate())
		{
			effect.deactivate();
			ongoingEffects.remove(effectType);
		}
	}

	private void pathFollowingHandle(float deltaTime)
	{
		if (!currentPath.isEmpty())
			followPath(deltaTime);
	}

	private void followPath(float deltaTime)
	{
		Point nearestTarget = currentPath.getFirst();
		makeStepToPoint(deltaTime, linkedState.getCollisionMap(), nearestTarget.x, nearestTarget.y);
		if (nearPoint(nearestTarget))
			currentPath.pollFirst();
	}

	private boolean nearPoint(Point nearestTarget)
	{
		return Math.abs(getX() - nearestTarget.x) + Math.abs(getY() - nearestTarget.y) < 2.0f;
	}

	protected void findPathTo(int x, int y)
	{
		//TODO FIX modulos
		int modulo = 3;
		Point startPoint = new Point(x - x%modulo, y - y%modulo);
		Point endPoint = new Point(x - x% modulo, y - y%modulo);
		DistanceComparator distanceComparator = new ManhattanDistanceComparator(endPoint);
		Collection<? extends Point> path = pathFinder.find(startPoint, endPoint, distanceComparator, collisionDetector);
		System.out.println("Path");
		currentPath = new PathSimplifier().simplify(path);
		Collections.reverse(currentPath);
	}

	public boolean isFollowingPath() {
    	return !currentPath.isEmpty();
	}

	@Override
    public void render(Batch batch)
    {
        super.render(batch);
        font.draw(batch, String.valueOf(properties.hp), getX() + 3, getY() + 40);

		drawDebugPath(batch);
	}

	private void drawDebugPath(Batch batch)
	{
		Texture debugTexture = Assets.get("debug.png");
		for(Point point : currentPath)
			batch.draw(debugTexture, point.x, point.y, 3, 3);
	}

	public void targetMonster(Monster target)
    {
        targetedMonster = target;
        target.addBeingTargetedBy(this);
    }

    public Monster getTargetedMonster()
    {
        return targetedMonster;
    }

    private void addBeingTargetedBy(Monster source)
    {
        targetedBy.add(source);
    }

    public void die()
    {
        linkedState.remove(getId());
        linkedState.sendToAll(PacketsMaker.makeRemovalPacket(getId()));
        for (Monster targetedBY : targetedBy)
            targetedBY.targetedMonster = null;
    }

    public void killed(Monster target)
    {
        targetedMonster = null;
    }

    public MonsterProperties getProperties()
    {
        return properties;
    }

    public boolean isInGame()
    {
    	return linkedState.has(getId());
    }

    protected void stopTargetingMonster()
    {
    	targetedMonster = null;
    }

    @Override
    public void onRemoval()
    {
    	for (Monster targetedBY : targetedBy)
            targetedBY.targetedMonster = null;
    	super.onRemoval();
    }

    @Override
	public void addItemAllowStacking(Item newItem)
    {
    	if(newItem instanceof StackableItem)
    	{
    		for(Item item : items.values())
    			if(item.getIdentifier().equals(newItem.getIdentifier()))
    			{
    				((StackableItem)item).stackWith((StackableItem)newItem);
    				return;
    			}

    	}
    	items.put(newItem.getId(), newItem);
    }

	@Override
	public void addItemDenyStacking(Item newItem)
	{
    	items.put(newItem.getId(), newItem);
	}

    @Override
    public void addItemsAllowStacking(Collection<Item> items)
    {
    	items.forEach(this::addItemAllowStacking);
    }

	@Override
	public void addItemsDenyStacking(Collection<Item> items)
	{
    	items.forEach(this::addItemDenyStacking);
	}

    @Override
	public void useItem(long id, PacketsSender packetSender)
    {
        useItem(id, this, packetSender);
    }

    @Override
	public void useItem(long id, Monster target, PacketsSender packetSender)
    {
        Item characterItem = items.get(id);
        if (characterItem == null)
            throw new CharacterDoesntHaveItemException(id);
        if (!(characterItem instanceof Useable))
            throw new CannotUseThisItemException(characterItem);
        ((Useable) characterItem).use(target, packetSender);
    }

    @Override
	public Collection<Item> getItems()
    {
        return items.values();
    }

	@Override
	public Item getItem(long itemId)
	{
		return items.get(itemId);
	}

    @Override
    public void removeItem(long id)
    {
    	if(items.remove(id) == null)
    		throw new NoSuchItemToRemoveException(id);
    }

    protected void addAbility(Ability ability)
    {
    	abilities.add(ability);
    }

    public void addEffect(Effect effect)
    {
    	Effect sameTypeEffect = ongoingEffects.get(effect.getClass());
    	if(sameTypeEffect != null && sameTypeEffect.canStackWith(effect))
    		sameTypeEffect.stackWithOtherEffect(effect);
    	else
    	{
    		effect.activate();
    		ongoingEffects.put(effect.getClass(), effect);
    	}
    }

    public void heal(int healPower)
    {
        MonsterProperties targetProperties = this.properties;
        if(targetProperties.hp >= actualStatistics.maxHp)
        	return;
        int previous = targetProperties.hp;
        targetProperties.hp += healPower;
        if(targetProperties.hp > actualStatistics.maxHp)
            targetProperties.hp = actualStatistics.maxHp;
        int delta = targetProperties.hp - previous;

        linkedState.sendToAll(PacketsMaker.makeHpNotifiedIncreasePacket(delta, getId()));
    }

	public Statistics getStatistics()
	{
		return actualStatistics;
	}

	public void modifyByDeltaStatistics(Statistics deltaStatistics)
	{
		StatisticsOperations.modifyByDeltaStatistics(actualStatistics, deltaStatistics);
	}

	public void recalculateStatistics()
	{
		Collection<ItemWithEffectWrapper> deactivatedEffects = deactivateEquipmentEffects();
		actualStatistics = StatisticsCalculator.calculateStatistics(properties);
		deactivatedEffects.forEach(wrapper -> applyEquipmentItemEffect(wrapper.getItem()));
	}

	private Collection<ItemWithEffectWrapper> deactivateEquipmentEffects()
	{
		Collection<ItemWithEffectWrapper> toDeactivate = new ArrayList<>(equipmentPositionToEffectMap.values());
		toDeactivate.forEach(wrapper -> wrapper.getEffect().deactivate());
		equipmentPositionToEffectMap.clear();
		return toDeactivate;
	}

	@Override
	public void equip(EquipableItem equipableItem, EquipmentPosition equipmentPosition)
	{
		if(!canEquip(equipableItem, equipmentPosition))
			throw new CannotEquipItemException(equipableItem, equipmentPosition);

		if (!this.containsItem(equipableItem.getId()))
			addItemDenyStacking(equipableItem);

		equipableItem.setEquipmentPosition(equipmentPosition);
		equipableItem.setInventoryPosition(null);
		applyEquipmentItemEffect(equipableItem);
	}

	public void applyEquipmentItemEffect(EquipableItem equipableItem)
	{
		Effect itemEffect = equipableItem.createStatisticsModificationEffect(this);
		addEffect(itemEffect);
		equipmentPositionToEffectMap.put(equipableItem.getEquipmentPosition(),
				new ItemWithEffectWrapper(equipableItem, itemEffect));
	}

	@Override
	public boolean canTakeOff(EquipableItem equipableItem, InventoryPosition destinationPosition)
	{
		return hasItemInPosition(equipableItem.getEquipmentPosition()) && !hasItemInPosition(destinationPosition);
	}

	private boolean hasItemInPosition(EquipmentPosition equipmentPosition)
	{
		return equipmentPositionToEffectMap.containsKey(equipmentPosition);
	}

	public Item getItem(EquipmentPosition equipmentPosition)
	{
		return Optional.ofNullable(equipmentPositionToEffectMap.get(equipmentPosition))
				.map(ItemWithEffectWrapper::getItem)
				.orElse(null);
	}

	@Override
	public void takeOff(EquipableItem equipableItem, InventoryPosition destinationPosition)
	{
		if(!canTakeOff(equipableItem, destinationPosition))
			throw new CannotTakeOffItemException(equipableItem, destinationPosition);

		ItemWithEffectWrapper wrapper = equipmentPositionToEffectMap.remove(equipableItem.getEquipmentPosition());
		if (wrapper.getItem().getId() != equipableItem.getId())
			throw new GameException("This should not happen");

		wrapper.getEffect().deactivate();

		equipableItem.setEquipmentPosition(EquipmentPosition.NONE);
		equipableItem.setInventoryPosition(destinationPosition);
	}

	public Item getItem(InventoryPosition position)
	{
		//Linear search, may need to improve
		return getItems().stream()
				.filter(item -> item.getInventoryPosition() != null)
				.filter(item -> item.getInventoryPosition().equals(position))
				.findAny().orElse(null);
	}

	public boolean hasItemInPosition(InventoryPosition position)
	{
		return getItem(position) != null;
	}
}
