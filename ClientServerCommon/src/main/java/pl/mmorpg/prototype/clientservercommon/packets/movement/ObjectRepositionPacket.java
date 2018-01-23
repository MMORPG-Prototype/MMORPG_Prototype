package pl.mmorpg.prototype.clientservercommon.packets.movement;

import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ObjectRepositionPacket implements GameObjectTargetPacket
{
	public long id;
	public float x;
	public float y;
	
	@Override
	public long getTargetId()
	{
		return id;
	}
}
