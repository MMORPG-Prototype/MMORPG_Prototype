package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ObjectCreationPacket
{
    public long id;
    public String identifier;
    public float x;
    public float y;
}
