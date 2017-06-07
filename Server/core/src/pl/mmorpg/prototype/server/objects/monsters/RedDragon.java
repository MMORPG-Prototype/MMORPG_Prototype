package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.RedDragonPropertiesBuilder;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.abilities.FireballAbility;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.GreenDragonLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;
import pl.mmorpg.prototype.server.states.PlayState;

public class RedDragon extends Dragon
{
	private static final MonsterLootGenerator dragonLootGenerator = new GreenDragonLootGenerator();
	
	public RedDragon(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, getDragonProperies(), collisionMap, playState);
		this.addAbility(new FireballAbility(this, (GameObjectsContainer)playState));
	}

	public static MonsterProperties getDragonProperies()
	{
		return new RedDragonPropertiesBuilder().build();
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}

	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		dragonLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		RedDragonBody dragonBody = new RedDragonBody(IdSupplier.getId(), getProperties().gold, items);
		return dragonBody;
	}

}
