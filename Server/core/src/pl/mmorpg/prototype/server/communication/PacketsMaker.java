package pl.mmorpg.prototype.server.communication;

import java.util.Collection;
import java.util.Iterator;

import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.HpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemSwapPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ManaDrainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.MpUpdatePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.PlayerCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestBoardInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.QuestDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerGoldRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.npcs.ShopItemWrapper;

public class PacketsMaker
{
	public static ObjectCreationPacket makeCreationPacket(GameObject object)
	{
		ObjectCreationPacket packet = new ObjectCreationPacket();
		packet.id = object.getId();
		packet.identifier = object.getIdentifier();
		packet.x = object.getX();
		packet.y = object.getY();
		return packet;
	}

	public static MonsterCreationPacket makeCreationPacket(Monster monster)
	{
		MonsterCreationPacket packet = new MonsterCreationPacket();
		packet.id = monster.getId();
		packet.identifier = monster.getIdentifier();
		packet.x = monster.getX();
		packet.y = monster.getY();
		packet.properties = monster.getProperties();
		return packet;
	}

	public static PlayerCreationPacket makeCreationPacket(PlayerCharacter player)
	{
		PlayerCreationPacket packet = new PlayerCreationPacket();
		packet.id = player.getId();
		packet.identifier = player.getIdentifier();
		packet.x = player.getX();
		packet.y = player.getY();
		packet.properties = player.getProperties();
		packet.data = PacketsMaker.makeCharacterPacket(player.getUserCharacterData());
		return packet;
	}

	public static ObjectRemovePacket makeRemovalPacket(long id)
	{
		return new ObjectRemovePacket(id);
	}

	public static ObjectRepositionPacket makeRepositionPacket(GameObject gameObject)
	{
		return makeRepositionPacket(gameObject.getId(), gameObject.getX(), gameObject.getY());
	}

	public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
	{
		ObjectRepositionPacket packet = new ObjectRepositionPacket();
		packet.id = id;
		packet.x = x;
		packet.y = y;
		return packet;
	}

	public static UserCharacterDataPacket makeCharacterPacket(UserCharacter character)
	{
		UserCharacterDataPacket packet = new UserCharacterDataPacket();
		packet.setId(character.getId());
		packet.setLevel(character.getLevel());
		packet.setHitPoints(character.getHitPoints());
		packet.setManaPoints(character.getManaPoints());
		packet.setNickname(character.getNickname());
		packet.setExperience(character.getExperience());
		packet.setStrength(character.getStrength());
		packet.setMagic(character.getMagic());
		packet.setDexitirity(character.getDexitirity());
		packet.setGold(character.getGold());
		packet.setStartingX(character.getLastLocationX());
		packet.setStartingY(character.getLastLocationY());
		return packet;
	}

	public static CharacterItemDataPacket makeItemPacket(CharacterItem item, long id)
	{
		CharacterItemDataPacket packet = new CharacterItemDataPacket();
		packet.setId(id);
		packet.setIdentifier(item.getIdentifier().toString());
		packet.setCount(item.getCount());
		InventoryPosition position = item.getInventoryPosition();
		packet.setInventoryPageNumber(position.getInventoryPageNumber());
		packet.setInventoryX(position.getInventoryX());
		packet.setInventoryY(position.getInventoryY());
		return packet;
	}

	public static CharacterItemDataPacket makeItemPacket(Item item)
	{
		InventoryPosition inventoryPosition = item.getInventoryPosition();
		return makeItemPacket(item, inventoryPosition.getInventoryPageNumber(), inventoryPosition.getInventoryX(),
				inventoryPosition.getInventoryY());
	}

	private static CharacterItemDataPacket makeItemPacket(Item item, int inventoryPage, int inventoryX, int inventoryY)
	{
		CharacterItemDataPacket packet = new CharacterItemDataPacket();
		packet.setId(item.getId());
		packet.setIdentifier(item.getIdentifier().toString());
		if (item instanceof StackableItem)
			packet.setCount(((StackableItem) item).getCount());
		packet.setInventoryPageNumber(inventoryPage);
		packet.setInventoryX(inventoryX);
		packet.setInventoryY(inventoryY);
		return packet;
	}

	public static MonsterTargetingReplyPacket makeTargetingReplyPacket(GameObject target)
	{
		if (target == null)
			throw new NullPointerException("Target cannot be null");
		MonsterTargetingReplyPacket packet = new MonsterTargetingReplyPacket();
		packet.monsterId = target.getId();
		return packet;
	}

	public static NormalDamagePacket makeNormalDamagePacket(long id, int damage)
	{
		NormalDamagePacket packet = new NormalDamagePacket();
		packet.setTargetId(id);
		packet.setDamage(damage);
		return packet;
	}

	public static ExperienceGainPacket makeExperienceGainPacket(long id, int experienceGain)
	{
		ExperienceGainPacket packet = new ExperienceGainPacket();
		packet.setTargetId(id);
		packet.setExperience(experienceGain);
		return packet;
	}

	public static HpChangeByItemUsagePacket makeHpNotifiedIncreasePacket(int delta, long targetId)
	{
		HpChangeByItemUsagePacket packet = new HpChangeByItemUsagePacket();
		packet.setHpChange(delta);
		packet.setMonsterTargetId(targetId);
		return packet;
	}

	public static MpChangeByItemUsagePacket makeMpChangeByItemUsagePacket(int delta, long targetId)
	{
		MpChangeByItemUsagePacket packet = new MpChangeByItemUsagePacket();
		packet.setMpChange(delta);
		packet.setMonsterTargetId(targetId);
		return packet;
	}

	public static FireDamagePacket makeFireDamagePacket(long targetId, int spellDamage)
	{
		FireDamagePacket fireDamagePacket = new FireDamagePacket();
		fireDamagePacket.setTargetId(targetId);
		fireDamagePacket.setDamage(spellDamage);
		return fireDamagePacket;
	}

	public static ManaDrainPacket makeManaDrainPacket(int manaDrain)
	{
		ManaDrainPacket packet = new ManaDrainPacket();
		packet.manaDrained = manaDrain;
		return packet;
	}

	public static ContainerContentPacket makeOpenContainerPacket(GameContainer container)
	{
		Collection<Item> items = container.getItems().values();
		CharacterItemDataPacket[] containerContent = createContainerContentPackets(items);
		ContainerContentPacket packet = new ContainerContentPacket();
		packet.setContentItems(containerContent);
		packet.setContainerId(container.getId());
		packet.setGoldAmount(container.getGoldAmount());
		return packet;
	}

	private static CharacterItemDataPacket[] createContainerContentPackets(Collection<Item> items)
	{
		CharacterItemDataPacket[] containerContent = new CharacterItemDataPacket[items.size()];
		Iterator<Item> it = items.iterator();
		for (int i = 0; i < containerContent.length; i++)
		{
			Item item = it.next();
			int inventoryPositionX = i;
			containerContent[i] = makeItemPacketWithStandardPosition(inventoryPositionX, item);
		}
		return containerContent;
	}

	private static CharacterItemDataPacket makeItemPacketWithStandardPosition(int inventoryPositionX, Item item)
	{
		int inventoryPage = 1;
		int inventoryPositionY = 1;
		CharacterItemDataPacket makeItemPacket = makeItemPacket(item, inventoryPage, inventoryPositionX,
				inventoryPositionY);
		return makeItemPacket;
	}

	public static ContainerItemRemovalPacket makeContainerItemRemovalPacket(long containerId, long itemId)
	{
		ContainerItemRemovalPacket packet = new ContainerItemRemovalPacket();
		packet.setContainerId(containerId);
		packet.setItemId(itemId);
		return packet;
	}

	public static UnacceptableOperationPacket makeUnacceptableOperationPacket(String errorMessage)
	{
		UnacceptableOperationPacket packet = new UnacceptableOperationPacket();
		packet.setErrorMessage(errorMessage);
		return packet;
	}

	public static ContainerGoldRemovalPacket makeContainerGoldRemovalPacket(long containerId, int gold)
	{
		ContainerGoldRemovalPacket packet = new ContainerGoldRemovalPacket();
		packet.setContainerId(containerId);
		packet.setGoldAmount(gold);
		return packet;
	}

	public static GoldReceivePacket makeGoldReceivePacket(int gold)
	{
		GoldReceivePacket packet = new GoldReceivePacket();
		packet.setGoldAmount(gold);
		return packet;
	}

	public static HpUpdatePacket makeHpUpdatePacket(long monsterId, int hp)
	{
		HpUpdatePacket packet = new HpUpdatePacket();
		packet.setNewHp(hp);
		packet.setId(monsterId);
		return packet;
	}

	public static MpUpdatePacket makeMpUpdatePacket(long monsterId, int mp)
	{
		MpUpdatePacket packet = new MpUpdatePacket();
		packet.setNewMp(mp);
		packet.setId(monsterId);
		return packet;
	}

	public static ShopItemsPacket makeShopItemsPacket(Collection<ShopItemWrapper> availableItems, long shopId)
	{

		ShopItemPacket[] itemsArray = availableItems.stream().map(PacketsMaker::makeShopItemPacket)
				.toArray(ShopItemPacket[]::new);

		ShopItemsPacket packet = new ShopItemsPacket();
		packet.setShopItems(itemsArray);
		packet.setShopId(shopId);
		return packet;
	}

	public static ShopItemPacket makeShopItemPacket(ShopItemWrapper itemWrapper)
	{
		ShopItemPacket singleItemPacketWrapper = new ShopItemPacket();
		CharacterItemDataPacket itemPacket = makeItemPacketWithStandardPosition(1, itemWrapper.getItem());
		singleItemPacketWrapper.setItem(itemPacket);
		singleItemPacketWrapper.setPrice(itemWrapper.getPrice());
		return singleItemPacketWrapper;
	}

	public static GoldAmountChangePacket makeGoldAmountChangePacket(int newGoldAmount)
	{
		GoldAmountChangePacket packet = new GoldAmountChangePacket();
		packet.setNewGoldAmount(newGoldAmount);
		return packet;
	}

	public static ScriptExecutionErrorPacket makeScriptExecutionErrorPacket(String error)
	{
		ScriptExecutionErrorPacket packet = new ScriptExecutionErrorPacket();
		packet.setError(error);
		return packet;
	}

	public static InventoryItemRepositionPacket makeInventoryItemRepositionPacket(InventoryPosition sourcePosition,
			InventoryPosition destinationPosition)
	{
		InventoryItemRepositionPacket packet = new InventoryItemRepositionPacket();
		
		packet.setSourcePageNumber(sourcePosition.getInventoryPageNumber());
		packet.setSourcePageX(sourcePosition.getInventoryX());
		packet.setSourcePageY(sourcePosition.getInventoryY());
		
		packet.setDestinationPageNumber(destinationPosition.getInventoryPageNumber());
		packet.setDestinationPageX(destinationPosition.getInventoryX());
		packet.setDestinationPageY(destinationPosition.getInventoryY());
		
		return packet;
	}

	public static InventoryItemSwapPacket makeInventoryItemSwapPacket(InventoryPosition firstPosition,
			InventoryPosition secondPosition)
	{
		InventoryItemSwapPacket packet = new InventoryItemSwapPacket();
		
		packet.setFirstPositionPageNumber(firstPosition.getInventoryPageNumber());
		packet.setFirstPositionPageX(firstPosition.getInventoryX());
		packet.setFirstPositionPageY(firstPosition.getInventoryY());
		
		packet.setSecondPositionPageNumber(secondPosition.getInventoryPageNumber());
		packet.setSecondPositionPageX(secondPosition.getInventoryX());
		packet.setSecondPositionPageY(secondPosition.getInventoryY());
		
		return packet;
	}

	public static ItemPutInQuickAccessBarPacket makeQuickAccessBarConfigElementPacket(QuickAccessBarConfigurationElement element)
	{
		ItemPutInQuickAccessBarPacket packet = new ItemPutInQuickAccessBarPacket();
		packet.setCellPosition(element.getFieldPosition());
		packet.setItemIdentifier(element.getItemIdentifier().toString());
		return packet;
	}

	public static ScriptResultInfoPacket makeScriptResultInfoPacket(String message)
	{
		ScriptResultInfoPacket packet = new ScriptResultInfoPacket();
		packet.setMessage(message);
		return packet;
	}

	public static QuestBoardInfoPacket makeQuestBoardInfoPacket(QuestBoard questBoard)
	{
		QuestDataPacket[] quests = questBoard.getQuests().stream()
							.map(PacketsMaker::makeQuestDataPacket)
							.toArray(QuestDataPacket[]::new);
		
		return makeQuestBoardInfoPacket(quests, questBoard.getId());
	}
	
	public static QuestDataPacket makeQuestDataPacket(Quest quest)
	{
		QuestDataPacket packet = new QuestDataPacket();
		packet.setDescription(quest.getDescription());
		packet.setName(quest.getName());
		return packet;
	}
	
	private static QuestBoardInfoPacket makeQuestBoardInfoPacket(QuestDataPacket[] quests, long questBoardId)
	{
		QuestBoardInfoPacket packet = new QuestBoardInfoPacket();
		packet.setQuests(quests);
		packet.setQuestBoardId(questBoardId);
		return packet;
	}

}
