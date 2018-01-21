package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
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
	public void doHandle(MpUpdatePacket packet)
	{
		playState.updateMp(packet.getId(), packet.getNewMp());
	}

}
