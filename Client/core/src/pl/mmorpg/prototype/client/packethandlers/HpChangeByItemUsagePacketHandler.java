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
	public boolean canHandle(HpChangeByItemUsagePacket packet)
	{
		return playState.has(packet.getMonsterTargetId());
	}
	
	@Override
	public boolean canOmmit(HpChangeByItemUsagePacket packet)
	{
		return true;
	}
	
}
