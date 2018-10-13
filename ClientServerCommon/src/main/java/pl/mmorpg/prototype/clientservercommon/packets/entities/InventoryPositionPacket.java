package pl.mmorpg.prototype.clientservercommon.packets.entities;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class InventoryPositionPacket
{
	private int inventoryPageNumber;
	private int inventoryX;
	private int inventoryY;
}
