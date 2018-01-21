package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;

public class ItemUsagePacketHandler extends PacketHandlerAdapter<ItemUsagePacket>
{
	private PlayState playState;

	public ItemUsagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(ItemUsagePacket packet)
	{
		playState.itemUsagePacketReceived(packet);
	}

}
