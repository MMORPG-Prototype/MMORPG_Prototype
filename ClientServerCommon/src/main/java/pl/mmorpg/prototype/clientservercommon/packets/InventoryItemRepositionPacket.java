package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class InventoryItemRepositionPacket
{
	private int sourcePageNumber;
	private int sourcePageX;
	private int sourcePageY;
	
	private int destinationPageNumber;
	private int destinationPageX;
	private int destinationPageY;
}
