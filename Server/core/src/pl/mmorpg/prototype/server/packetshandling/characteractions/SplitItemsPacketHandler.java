package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SplitItemsPacket;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class SplitItemsPacketHandler extends PacketHandlerBase<SplitItemsPacket>
{
	private final PlayState playState;
	private final GameDataRetriever gameDataRetriever;

	public SplitItemsPacketHandler(PlayState playState, GameDataRetriever gameDataRetriever)
	{
		this.playState = playState;
		this.gameDataRetriever = gameDataRetriever;
	}

	@Override
	public void handle(Connection connection, SplitItemsPacket packet)
	{
		long characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		PlayerCharacter player = (PlayerCharacter) playState.getObject(characterId);
		InventoryPosition splitDestination = new InventoryPosition(packet.getDestinationPageNumber(),
				packet.getDestinationPageX(), packet.getDestinationPageY());
		StackableItem sourceItem = (StackableItem) player.getItem(packet.getSourceItemId());
		if (sourceItem == null)
		{
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Source item does not exist"));
			return;
		}
		
		Item destinationItem = player.getItem(splitDestination);
		if (destinationItem == null)
		{
			Item newItem = GameItemsFactory.produce(sourceItem.getIdentifier(), packet.getSplitItemAmount(), IdSupplier.getId(),
					splitDestination, EquipmentPosition.NONE);
			player.addItemDenyStacking(newItem);
			sourceItem.modifyAmount(-packet.getSplitItemAmount());
			connection.sendTCP(PacketsMaker.makeItemPacket(newItem));
			InventoryPosition inventoryPosition = sourceItem.getInventoryPosition();
			connection.sendTCP(PacketsMaker.makeStackableItemAmountChangePacket(-packet.getSplitItemAmount(), inventoryPosition));
			return;
		}
		
		if (destinationItem.getIdentifier().equals(sourceItem.getIdentifier()))
		{
			sourceItem.modifyAmount(-packet.getSplitItemAmount());
			((StackableItem) destinationItem).modifyAmount(packet.getSplitItemAmount());
			connection.sendTCP(PacketsMaker.makeStackableItemAmountChangePacket(-packet.getSplitItemAmount(), sourceItem.getInventoryPosition()));
			connection.sendTCP(PacketsMaker.makeStackableItemAmountChangePacket(packet.getSplitItemAmount(), destinationItem.getInventoryPosition()));
		}
		else
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("Destination field is taken"));
		
	}


}
