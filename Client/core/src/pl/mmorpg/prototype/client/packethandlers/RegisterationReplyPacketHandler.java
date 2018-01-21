package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.RegisterationState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;

public class RegisterationReplyPacketHandler extends PacketHandlerAdapter<RegisterationReplyPacket>
{

	private StateManager states;

	public RegisterationReplyPacketHandler(StateManager states)
	{
		this.states = states;
	}
	
	@Override
	public void handle(RegisterationReplyPacket replyPacket)
	{
		RegisterationState registerationState = (RegisterationState) states.usedState();
		registerationState.registerationReplyReceived(replyPacket);
	}

}
