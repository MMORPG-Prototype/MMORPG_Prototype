package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;

public class ExperienceGainPacketHandler extends PacketHandlerBase<ExperienceGainPacket>
{
	private PlayState playState;

	public ExperienceGainPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void doHandle(ExperienceGainPacket packet)
	{
		playState.experienceGainPacketReceived(packet);
	}
	
	@Override
	public boolean canBeHandled(ExperienceGainPacket packet)
	{
		return playState.isInitialized() && playState.has(((ExperienceGainPacket)packet).getTargetId());
	}

}
