package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;

public class CharacterCreationReplyPacketHandler extends PacketHandlerBase<CharacterCreationReplyPacket>
{

	private StateManager states;

	public CharacterCreationReplyPacketHandler(StateManager states)
	{
		this.states = states;
	}
	
	@Override
	public void handlePacket(CharacterCreationReplyPacket characterPacket)
	{
		ChoosingCharacterState choosingCharState = (ChoosingCharacterState) states.usedState();
		choosingCharState.userCharacterCreationReplyReceived(characterPacket);
	}

}
