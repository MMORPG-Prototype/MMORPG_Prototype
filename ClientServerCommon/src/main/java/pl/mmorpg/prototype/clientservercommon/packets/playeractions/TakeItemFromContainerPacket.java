package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class TakeItemFromContainerPacket
{
	private long containerId;
	private long itemId;
	private int desiredInventoryPage;
	private int desiredInventoryX;
	private int desiredInventoryY;
}
