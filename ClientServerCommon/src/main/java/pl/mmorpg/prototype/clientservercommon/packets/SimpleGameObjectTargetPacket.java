package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class SimpleGameObjectTargetPacket implements GameObjectTargetPacket
{
	public long id = -1;
	
	@Override
	public long getTargetId()
	{
		return id;
	}

}
