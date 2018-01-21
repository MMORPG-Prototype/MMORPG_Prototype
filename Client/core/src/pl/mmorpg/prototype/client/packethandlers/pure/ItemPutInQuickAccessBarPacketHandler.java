package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ItemPutInQuickAccessBarPacket;

public class ItemPutInQuickAccessBarPacketHandler extends PacketHandlerBase<ItemPutInQuickAccessBarPacket>
{
	private PlayState playState;

	public ItemPutInQuickAccessBarPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ItemPutInQuickAccessBarPacket packet)
	{
		playState.putItemInQuickAccessBarPacketReceived(packet);
	}

}
