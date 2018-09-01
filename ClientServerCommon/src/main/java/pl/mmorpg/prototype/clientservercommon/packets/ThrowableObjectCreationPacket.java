package pl.mmorpg.prototype.clientservercommon.packets;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class ThrowableObjectCreationPacket extends ObjectCreationPacket
{
	public ObjectBasicData targetData;
}
