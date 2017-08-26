package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Data
@Registerable
public class ItemPutInQuickAccessBarPacket
{
	private int cellPosition;
	private String itemIdentifier;
}
