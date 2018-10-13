package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SwitchEquipmentItemPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.TakeOffItemPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class TakeOffItemPacketHandler extends PacketHandlerBase<TakeOffItemPacket>
{
	private final GameDataRetriever gameDataRetriever;
	private final PlayState playState;

	public TakeOffItemPacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		this.gameDataRetriever = gameDataRetriever;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, TakeOffItemPacket packet)
	{
		int characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		Monster player = (Monster) playState.getObject(characterId);
		Item item = player.getItem(packet.getItemId());
		InventoryPosition destinationPosition = new InventoryPosition(packet.getDestinationPosition().getInventoryPageNumber(),
			packet.getDestinationPosition().getInventoryX(), packet.getDestinationPosition().getInventoryY());
		if (player.getItem(destinationPosition) != null)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("There is already an item "
					+ "in this equipment position, should use " + SwitchEquipmentItemPacket.class.getSimpleName()));
		else if (item == null)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You do not have this item"));
		else if (!(item instanceof EquipableItem))
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("This item is not equipable"));
		else if (!player.canTakeOff((EquipableItem)item, destinationPosition))
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You can not take off this item this way"));
		else
		{
			EquipmentPosition sourcePosition = ((EquipableItem) item).getEquipmentPosition();
			player.takeOff((EquipableItem)item, destinationPosition);
			connection.sendTCP(PacketsMaker.makeItemTookOffSuccessfullyPacket(sourcePosition, packet.getDestinationPosition()));
		}
	}
}
