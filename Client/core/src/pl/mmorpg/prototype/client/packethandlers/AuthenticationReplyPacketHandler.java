package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.AuthenticationState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;

public class AuthenticationReplyPacketHandler extends PacketHandlerAdapter<AuthenticationReplyPacket>
{
	private StateManager states;

	public AuthenticationReplyPacketHandler(StateManager states)
	{
		this.states = states;
	}

	@Override
	public void handle(AuthenticationReplyPacket replyPacket)
	{
		AuthenticationState authenticationState = (AuthenticationState) states.usedState();
		authenticationState.authenticationReplyReceived(replyPacket);	
	}
}
