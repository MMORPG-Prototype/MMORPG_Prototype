package pl.mmorpg.prototype.client.communication;

import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class PacketsMaker
{

    public static ObjectRemovePacket makeRemovalPacket(int id)
    {
        return new ObjectRemovePacket(id);
    }

    public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
    {
        ObjectRepositionPacket packet = new ObjectRepositionPacket();
        packet.id = id;
        packet.x = x;
        packet.y = y;
        return packet;
    }
}
