package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;

public class ChatMessagePacketHandler extends PacketHandlerBase<ChatMessagePacket>
{
	private PlayState playState;

	public ChatMessagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(ChatMessagePacket packet)
	{		
		playState.chatMessagePacketReceived(packet);
	}

}
