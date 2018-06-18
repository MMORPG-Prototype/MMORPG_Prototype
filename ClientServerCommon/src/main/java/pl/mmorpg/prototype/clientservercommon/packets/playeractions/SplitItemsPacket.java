package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class SplitItemsPacket
{
	private long sourceItemId;
	private int splitItemAmount;
	private int destinationPageNumber;
	private int destinationPageX;
	private int destinationPageY;
}
