package pl.mmorpg.prototype.clientservercommon.packets.movement;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ObjectRepositionPacket
{
	public long id;
	public float x;
	public float y;
}
