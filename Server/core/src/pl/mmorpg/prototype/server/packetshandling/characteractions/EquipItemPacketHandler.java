package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.EquipItemPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SwitchEquipmentItemPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class EquipItemPacketHandler extends PacketHandlerBase<EquipItemPacket>
{
	private final GameDataRetriever gameDataRetriever;
	private final PlayState playState;

	public EquipItemPacketHandler(GameDataRetriever gameDataRetriever, PlayState playState)
	{
		this.gameDataRetriever = gameDataRetriever;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, EquipItemPacket packet)
	{
		int characterId = gameDataRetriever.getCharacterIdByConnectionId(connection.getID());
		Monster player = (Monster) playState.getObject(characterId);
		Item item = player.getItem(packet.getItemId());
		EquipmentPosition equipmentPosition = EquipmentPosition.valueOf(packet.getEquipmentPosition());
		if (player.getItem(equipmentPosition) != null)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("There is already an item "
					+ "in this equipment position, should use " + SwitchEquipmentItemPacket.class.getSimpleName()));
		else if (item == null)
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You do not have this item"));
		else if (!(item instanceof EquipableItem))
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("This item is not equipable"));
		else if (!player.canEquip((EquipableItem)item, equipmentPosition))
			connection.sendTCP(PacketsMaker.makeUnacceptableOperationPacket("You can not equip this item this way"));
		else
		{
			player.equip((EquipableItem)item, equipmentPosition);
			connection.sendTCP(PacketsMaker.makeItemEquippedSuccessfullyPacket(packet.getItemId(), packet.getEquipmentPosition()));
		}
	}
}
