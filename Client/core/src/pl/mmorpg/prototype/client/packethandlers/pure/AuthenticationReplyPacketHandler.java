package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.AuthenticationState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;

public class AuthenticationReplyPacketHandler extends PacketHandlerBase<AuthenticationReplyPacket>
{
	private StateManager states;

	public AuthenticationReplyPacketHandler(StateManager states)
	{
		this.states = states;
	}

	@Override
	public void doHandle(AuthenticationReplyPacket replyPacket)
	{
		AuthenticationState authenticationState = (AuthenticationState) states.usedState();
		authenticationState.authenticationReplyReceived(replyPacket);	
	}
}
