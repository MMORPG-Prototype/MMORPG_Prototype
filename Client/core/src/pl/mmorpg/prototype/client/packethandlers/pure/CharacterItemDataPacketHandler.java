package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class CharacterItemDataPacketHandler extends PacketHandlerBase<CharacterItemDataPacket>
{
	private PlayState playState;

	public CharacterItemDataPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(CharacterItemDataPacket packet)
	{
		playState.newItemPacketReceived(packet);
	}

}
