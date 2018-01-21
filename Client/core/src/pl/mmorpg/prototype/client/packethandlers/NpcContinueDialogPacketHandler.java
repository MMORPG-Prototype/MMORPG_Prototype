package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcContinueDialogPacket;

public class NpcContinueDialogPacketHandler extends PacketHandlerAdapter<NpcContinueDialogPacket>
{
	private PlayState playState;

	public NpcContinueDialogPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(NpcContinueDialogPacket packet)
	{
		playState.continueNpcConversation(packet);
	}

}
