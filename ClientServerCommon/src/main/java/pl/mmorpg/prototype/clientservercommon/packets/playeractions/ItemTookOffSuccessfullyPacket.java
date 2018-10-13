package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.entities.InventoryPositionPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class ItemTookOffSuccessfullyPacket
{
	private String equipmentPosition;
	private InventoryPositionPacket destinationPosition;
}
