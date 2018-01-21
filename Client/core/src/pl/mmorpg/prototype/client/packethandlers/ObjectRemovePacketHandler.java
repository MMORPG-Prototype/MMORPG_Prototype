package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;

public class ObjectRemovePacketHandler extends PacketHandlerAdapter<ObjectRemovePacket>
{
	private PlayState playState;
	
	public ObjectRemovePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(ObjectRemovePacket packet)
	{
		playState.removeObject(packet.id);	
	}

}
