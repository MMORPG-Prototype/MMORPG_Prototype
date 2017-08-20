package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class InventoryItemSwapPacket
{
	private int firstPositionPageNumber;
	private int firstPositionPageX;
	private int firstPositionPageY;
	
	private int secondPositionPageNumber;
	private int secondPositionPageX;
	private int secondPositionPageY;	
}
