package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcStartDialogPacket;

public class NpcStartDialogPacketHandler extends PacketHandlerAdapter<NpcStartDialogPacket>
{
	private PlayState playState;

	public NpcStartDialogPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(NpcStartDialogPacket packet)
	{
		playState.npcStartDialogPacketReceived(packet);		
	}

}
