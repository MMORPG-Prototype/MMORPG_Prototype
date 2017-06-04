package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.HpChangeByItemUsagePacket;

public class HpChangeByItemUsagePacketHandler extends PacketHandlerBase<HpChangeByItemUsagePacket>
{
	private PlayState playState;

	public HpChangeByItemUsagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(HpChangeByItemUsagePacket packet)
	{
		playState.hpChangeByItemUsagePacketReceived(packet);
	}
	
	@Override
	public boolean canBeHandled(Object packet)
	{
		return playState.isInitialized();
	}

}
