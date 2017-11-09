package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.InventoryItemRepositionRequestPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.objects.monsters.InventoryRepositionableItemsOwner;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class InventoryItemRepositionRequestPacketHandler extends PacketHandlerBase<InventoryItemRepositionRequestPacket>
{
	private PlayState playState;
	private GameDataRetriever gameData;

	public InventoryItemRepositionRequestPacketHandler(GameDataRetriever gameData, PlayState playState)
	{
		this.gameData = gameData;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, InventoryItemRepositionRequestPacket packet)
	{
		int characterId = gameData.getCharacterIdByConnectionId(connection.getID());
		InventoryRepositionableItemsOwner playerCharacter = (InventoryRepositionableItemsOwner) playState
				.getObject(characterId);
		Item item = playerCharacter.getItem(packet.getTargetItemId());
		InventoryPosition desiredPosition = new InventoryPosition(packet.getInventoryPageNumber(),
				packet.getInventoryPageX(), packet.getInventoryPageY());
		InventoryPosition sourcePosition = item.getInventoryPosition();
		Item itemInDestinationPosition = playerCharacter.getItem(desiredPosition);
		if (itemInDestinationPosition == null)
		{
			connection.sendTCP(
					PacketsMaker.makeInventoryItemRepositionPacket(sourcePosition, desiredPosition));
			item.setInventoryPosition(desiredPosition);
		} else if(itemInDestinationPosition instanceof StackableItem && 
				itemInDestinationPosition.getIdentifier().equals(item.getIdentifier()))
		{
			connection.sendTCP(PacketsMaker.makeInventoryItemStackPacket(sourcePosition, desiredPosition));
			((StackableItem)itemInDestinationPosition).stackWith((StackableItem)item);
			playerCharacter.removeItem(item.getId());
		}
		else
		{
			connection.sendTCP(PacketsMaker.makeInventoryItemSwapPacket(sourcePosition, desiredPosition));
			itemInDestinationPosition.setInventoryPosition(sourcePosition);;
			item.setInventoryPosition(desiredPosition);
		}
	}

}
