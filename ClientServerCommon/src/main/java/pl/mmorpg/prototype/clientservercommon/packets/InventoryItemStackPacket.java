package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class InventoryItemStackPacket
{
	private int firstPositionPageNumber;
	private int firstPositionPageX;
	private int firstPositionPageY;
	
	private int secondPositionPageNumber;
	private int secondPositionPageX;
	private int secondPositionPageY;
}
