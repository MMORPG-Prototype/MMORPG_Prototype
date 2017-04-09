package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.ChoosingCharacterState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class UserCharacterDataArrayPacketHandler extends PacketHandlerBase<UserCharacterDataPacket[]>
{
	private StateManager states;

	public UserCharacterDataArrayPacketHandler(StateManager states)
	{
		this.states = states;
	}

	@Override
	public void handlePacket(UserCharacterDataPacket[] characterPackets)
	{
		ChoosingCharacterState choosingCharState = (ChoosingCharacterState) states.usedState();
		choosingCharState.userCharactersDataReceived(characterPackets);
	}

}