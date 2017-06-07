package pl.mmorpg.prototype.clientservercommon.packets;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class ShopItemsPacket
{
	private ShopItemPacket[] shopItems;
	private long shopId;
}
