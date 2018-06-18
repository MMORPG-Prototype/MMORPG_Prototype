package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class StackableItemAmountChangePacket
{
	private int itemAmount;
	private int itemPageNumber;
	private int itemPageX;
	private int itemPageY;
}
