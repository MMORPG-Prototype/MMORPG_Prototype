package pl.mmorpg.prototype.client.communication;

import java.awt.Point;

import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.NpcConversationAnwserChoosenPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptCodePacket;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.AcceptQuestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BuyFromShopPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenShopPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveGoldRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveItemRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakingGoldFromContainerPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TargetMonsterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.FireballSpellUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.spells.HealSpellUsagePacket;

public class PacketsMaker
{
	public static ObjectRemovePacket makeRemovalPacket(int id)
	{
		return new ObjectRemovePacket(id);
	}

	public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
	{
		ObjectRepositionPacket packet = new ObjectRepositionPacket();
		packet.id = id;
		packet.x = x;
		packet.y = y;
		return packet;
	}

	public static ChatMessagePacket makeChatMessagePacket(String message)
	{
		ChatMessagePacket packet = new ChatMessagePacket();
		packet.setMessage(message);
		return packet;
	}

	public static BoardClickPacket makeBoardClickPacket(float x, float y)
	{
		BoardClickPacket packet = new BoardClickPacket();
		packet.gameX = (int) x;
		packet.gameY = (int) y;
		return packet;
	}

	public static <T extends Item & ItemUseable> ItemUsagePacket makeItemUsagePacket(T item, Monster target)
	{
		ItemUsagePacket packet = new ItemUsagePacket();
		packet.setItemId(item.getId());
		packet.setTargetId(target.getId());
		return packet;
	}

	public static FireballSpellUsagePacket makeFireballSpellUsagePacket()
	{
		FireballSpellUsagePacket packet = new FireballSpellUsagePacket();
		return packet;
	}

	public static OpenContainterPacket makeContainterOpeningPacket(float x, float y)
	{
		OpenContainterPacket packet = new OpenContainterPacket();
		packet.gameX = (int) x;
		packet.gameY = (int) y;
		return packet;
	}

	public static TakeItemFromContainerPacket makeTakeItemFromContainerPacket(long containerId, long itemId,
			ItemInventoryPosition position)
	{
		TakeItemFromContainerPacket packet = new TakeItemFromContainerPacket();
		packet.setContainerId(containerId);
		packet.setItemId(itemId);
		packet.setDesiredInventoryPage(position.getPageNumber());
		Point inventoryPoint = position.getPosition();
		packet.setDesiredInventoryX(inventoryPoint.x);
		packet.setDesiredInventoryY(inventoryPoint.y);
		return packet;
	}

	public static TakingGoldFromContainerPacket makeTakingGoldFromContainerPacket(long containerId)
	{
		TakingGoldFromContainerPacket packet = new TakingGoldFromContainerPacket();
		packet.setContainerId(containerId);
		return packet;
	}

	public static BuyFromShopPacket makeBuyFromShopPacket(long shopId, long itemId, int amount,
			ItemInventoryPosition suitePosition)
	{
		BuyFromShopPacket packet = new BuyFromShopPacket();
		packet.setShopId(shopId);
		packet.setItemId(itemId);
		packet.setAmount(amount);
		packet.setDesiredInventoryPage(suitePosition.getPageNumber());
		packet.setDesiredInventoryX(suitePosition.getPosition().x);
		packet.setDesiredInventoryY(suitePosition.getPosition().y);
		return packet;
	}

	public static ScriptCodePacket makeScriptCodePacket(String command)
	{
		ScriptCodePacket packet = new ScriptCodePacket();
		packet.setCode(command);
		return packet;
	}

	public static InventoryItemRepositionRequestPacket makeInventoryItemRepositionRequestPacket(long targetItemId,
			ItemInventoryPosition desiredPosition)
	{
		InventoryItemRepositionRequestPacket packet = new InventoryItemRepositionRequestPacket();
		packet.setTargetItemId(targetItemId);
		packet.setInventoryPageNumber(desiredPosition.getPageNumber());
		packet.setInventoryPageX(desiredPosition.getPosition().x);
		packet.setInventoryPageY(desiredPosition.getPosition().y);
		return packet;
	}

	public static ItemPutInQuickAccessBarPacket makeItemPutInQuickAccessBarPacket(String itemIdentifier,
			int cellPosition)
	{
		ItemPutInQuickAccessBarPacket packet = new ItemPutInQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		packet.setItemIdentifier(itemIdentifier);
		return packet;
	}
	
	public static SpellPutInQuickAccessBarPacket makeSpellPutInQuickAccessBarPacket(SpellIdentifiers spellIdentifier, int cellPosition)
	{
		SpellPutInQuickAccessBarPacket packet = new SpellPutInQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		packet.setSpellIdentifier(spellIdentifier);
		return packet;
	}

	public static ItemRemovedFromQuickAccessBarPacket makeItemRemovedFromQuickAccessBarPacket(int cellPosition)
	{
		ItemRemovedFromQuickAccessBarPacket packet = new ItemRemovedFromQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		return packet;
	}
	
	public static SpellRemovedFromQuickAccessBarPacket makeSpellRemovedFromQuickAccessBarPacket(int cellPosition)
	{
		SpellRemovedFromQuickAccessBarPacket packet = new SpellRemovedFromQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		return packet;
	}

    public static RetrieveItemRewardPacket makeRetrieveItemRewardPacket(String itemIdentifier, int numberOfItems,
            ItemInventoryPosition desiredItemPosition, String questName)
    {
        RetrieveItemRewardPacket packet = new RetrieveItemRewardPacket();
        packet.setDesiredInventoryPage(desiredItemPosition.getPageNumber());
        packet.setDesiredInventoryX(desiredItemPosition.getPosition().x);
        packet.setDesiredInventoryY(desiredItemPosition.getPosition().y);
        packet.setItemIdentifier(itemIdentifier);
        packet.setNumberOfItems(numberOfItems);
        packet.setQuestName(questName);
        return packet;
    }

    public static RetrieveGoldRewardPacket makeRetrieveGoldRewardPacket(String questName)
    {
        RetrieveGoldRewardPacket packet = new RetrieveGoldRewardPacket();
        packet.setQuestName(questName);
        return packet;
    }

    public static AcceptQuestPacket makeAcceptQuestPacket(String questName)
    {
        AcceptQuestPacket packet = new AcceptQuestPacket();
        packet.setQuestName(questName);
        return packet;
    }

	public static NpcConversationAnwserChoosenPacket makeNpcConversationAnwserChoosenPacket(long npcId, String anwser)
	{
		NpcConversationAnwserChoosenPacket packet = new NpcConversationAnwserChoosenPacket();
		packet.setNpcId(npcId);
		packet.setAnwser(anwser);
		return packet;
	}

	public static HealSpellUsagePacket makeHealSpellUsagePacket()
	{
		return new HealSpellUsagePacket();
	}
	
	public static OpenShopPacket makeOpenShopPacket(long shopNpcId)
	{
		OpenShopPacket packet = new OpenShopPacket();
		packet.setShopNpcId(shopNpcId);
		return packet;
	}
	
	public static TargetMonsterPacket makeTargetMonsterPacket(long monsterId)
	{
		TargetMonsterPacket packet = new TargetMonsterPacket();
		packet.setMonsterId(monsterId);
		return packet;
	}

}
