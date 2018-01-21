package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcStartDialogPacket;

public class NpcStartDialogPacketHandler extends PacketHandlerBase<NpcStartDialogPacket>
{
	private PlayState playState;

	public NpcStartDialogPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void doHandle(NpcStartDialogPacket packet)
	{
		playState.npcStartDialogPacketReceived(packet);		
	}

}
