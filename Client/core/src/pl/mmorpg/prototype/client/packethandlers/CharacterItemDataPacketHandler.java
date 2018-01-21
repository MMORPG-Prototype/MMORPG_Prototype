package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class CharacterItemDataPacketHandler extends PacketHandlerAdapter<CharacterItemDataPacket>
{
	private PlayState playState;

	public CharacterItemDataPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(CharacterItemDataPacket packet)
	{
		playState.newItemPacketReceived(packet);
	}

}
