package pl.mmorpg.prototype.client.communication;

import java.awt.Point;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptCodePacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BoardClickPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.BuyFromShopPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.FireballSpellUsagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemRemovedFromQuickAccessBarPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeItemFromContainerPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakingGoldFromContainerPacket;

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

	public static BuyFromShopPacket makeBuyFromShopPacket(long shopId, long itemId, int amount)
	{
		BuyFromShopPacket packet = new BuyFromShopPacket();
		packet.setShopId(shopId);
		packet.setItemId(itemId);
		packet.setAmount(amount);
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

	public static ItemPutInQuickAccessBarPacket makeItemPutInQuickAccessBarPacket(String itemIdentifier, int cellPosition)
	{
		ItemPutInQuickAccessBarPacket packet = new ItemPutInQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		packet.setItemIdentifier(itemIdentifier);
		return packet;
	}

	public static ItemRemovedFromQuickAccessBarPacket makeItemRemovedFromQuickAccessBarPacket(int cellPosition)
	{
		ItemRemovedFromQuickAccessBarPacket packet = new ItemRemovedFromQuickAccessBarPacket();
		packet.setCellPosition(cellPosition);
		return packet;
	}
}
