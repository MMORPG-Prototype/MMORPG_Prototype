package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MpChangeByItemUsagePacket;

public class MpChangeByItemUsagePacketHandler extends PacketHandlerBase<MpChangeByItemUsagePacket>
{
	private PlayState playState;

	public MpChangeByItemUsagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(MpChangeByItemUsagePacket packet)
	{
		playState.mpChangeByItemUsagePacketReceived(packet);
	}

}
