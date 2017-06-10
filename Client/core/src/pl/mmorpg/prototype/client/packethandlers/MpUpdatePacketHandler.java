package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.MpUpdatePacket;

public class MpUpdatePacketHandler extends PacketHandlerBase<MpUpdatePacket>
{
	private PlayState playState;

	public MpUpdatePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(MpUpdatePacket packet)
	{
		playState.updateMp(packet.getId(), packet.getNewMp());
	}

}
