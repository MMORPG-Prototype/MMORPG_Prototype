package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectCreationPacketHandler extends PacketHandlerBase<ObjectCreationPacket>
{
    public ObjectCreationPacketHandler(PlayState playState)
    {
    }

    @Override
    public void doHandle(ObjectCreationPacket packet)
    {
    	System.out.println("Unused");
//    	Function<CollisionMap<GameObject>, GameObject> objectCreator = 
//				collisionMap -> objectsFactory.produce(packet, collisionMap);
//        playState.add(objectCreator);
    }

}
