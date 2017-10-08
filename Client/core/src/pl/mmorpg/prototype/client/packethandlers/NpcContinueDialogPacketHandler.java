package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.NpcContinueDialogPacket;

public class NpcContinueDialogPacketHandler extends PacketHandlerBase<NpcContinueDialogPacket>
{
	private PlayState playState;

	public NpcContinueDialogPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handlePacket(NpcContinueDialogPacket packet)
	{
		playState.continueNpcConversation(packet);
	}

}
