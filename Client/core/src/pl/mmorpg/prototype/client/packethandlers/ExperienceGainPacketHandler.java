package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;

public class ExperienceGainPacketHandler extends PacketHandlerAdapter<ExperienceGainPacket>
{
	private PlayState playState;

	public ExperienceGainPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(ExperienceGainPacket packet)
	{
		playState.experienceGainPacketReceived(packet);
	}
	
	@Override
	public boolean canBeHandled(ExperienceGainPacket packet)
	{
		return playState.isInitialized() && playState.has(((ExperienceGainPacket)packet).getTargetId());
	}

}
