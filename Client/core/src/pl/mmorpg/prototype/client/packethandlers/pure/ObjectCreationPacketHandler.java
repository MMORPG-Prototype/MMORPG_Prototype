package pl.mmorpg.prototype.client.packethandlers.pure;

import java.util.function.Function;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectCreationPacketHandler extends PacketHandlerBase<ObjectCreationPacket>
{
    private PlayState playState;
    private static ObjectsFactory objectsFactory = new ObjectsFactory();

    public ObjectCreationPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void doHandle(ObjectCreationPacket packet)
    {
    	Function<CollisionMap<GameObject>, GameObject> objectCreator = 
				collisionMap -> objectsFactory.produce(packet, collisionMap);
        playState.add(objectCreator);
    }

}
