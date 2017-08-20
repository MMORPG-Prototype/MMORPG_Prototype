package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class InventoryItemRepositionRequestPacket
{
	private long targetItemId;
	private int inventoryPageNumber;
	private int inventoryPageX;
	private int inventoryPageY;
}
