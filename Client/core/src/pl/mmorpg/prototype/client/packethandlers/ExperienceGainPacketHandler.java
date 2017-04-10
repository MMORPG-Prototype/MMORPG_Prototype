package pl.mmorpg.prototype.client.packethandlers;

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
	public void handlePacket(ExperienceGainPacket packet)
	{
		playState.experienceGainPacketReceived(packet);
	}

}
