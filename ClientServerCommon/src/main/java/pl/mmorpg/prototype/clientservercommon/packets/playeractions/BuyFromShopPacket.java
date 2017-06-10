package pl.mmorpg.prototype.clientservercommon.packets.playeractions;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class BuyFromShopPacket
{
	private long shopId;
	private long itemId;
	private int amount;
}
