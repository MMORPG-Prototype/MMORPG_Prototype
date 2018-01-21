package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;

public class ChatMessageReplyPacketHandler extends PacketHandlerBase<ChatMessageReplyPacket>
{
	private PlayState playState;

	public ChatMessageReplyPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ChatMessageReplyPacket packet)
	{		
		playState.chatMessagePacketReceived(packet);
	}

}
