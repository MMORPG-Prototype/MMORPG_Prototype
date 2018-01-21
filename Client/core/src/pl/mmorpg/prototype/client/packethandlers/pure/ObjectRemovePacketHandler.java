package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;

public class ObjectRemovePacketHandler extends PacketHandlerBase<ObjectRemovePacket>
{
	private PlayState playState;
	
	public ObjectRemovePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ObjectRemovePacket packet)
	{
		playState.removeObject(packet.id);	
	}

}
