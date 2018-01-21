package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ShopItemsPacket;

public class ShopItemsPacketHandler extends PacketHandlerBase<ShopItemsPacket>
{
	private PlayState playState;

	public ShopItemsPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void doHandle(ShopItemsPacket packet)
	{
		playState.openShopDialog(packet.getShopItems(), packet.getShopId());
	}

}
