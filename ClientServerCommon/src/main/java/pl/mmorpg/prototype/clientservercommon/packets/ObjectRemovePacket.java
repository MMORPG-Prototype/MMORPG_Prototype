package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ObjectRemovePacket extends GameObjectTargetPacket
{

	public ObjectRemovePacket()
	{
	}

	public ObjectRemovePacket(int id)
	{
		this.id = id;
	}
}
