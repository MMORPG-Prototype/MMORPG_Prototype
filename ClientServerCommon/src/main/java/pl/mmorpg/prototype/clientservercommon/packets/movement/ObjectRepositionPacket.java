package pl.mmorpg.prototype.clientservercommon.packets.movement;

import pl.mmorpg.prototype.clientservercommon.packets.GameObjectTargetPacket;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ObjectRepositionPacket extends GameObjectTargetPacket
{
	public float x;
	public float y;
}
