package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
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
	public void doHandle(HpChangeByItemUsagePacket packet)
	{
		playState.hpChangeByItemUsagePacketReceived(packet);
	}
	
	@Override
	public boolean canBeHandled(HpChangeByItemUsagePacket packet)
	{
		return playState.has(packet.getMonsterTargetId());
	}
	
	@Override
	public boolean canBeOmmited(HpChangeByItemUsagePacket packet)
	{
		return true;
	}
	
}
