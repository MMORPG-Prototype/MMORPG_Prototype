package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectCreationPacketHandler extends PacketHandlerBase<ObjectCreationPacket>
{
	private PlayState playState;

	public ObjectCreationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(ObjectCreationPacket packet)
	{
		GameObject newObject = ObjectsFactory.produce(packet);
		playState.add(newObject);
	}

}
