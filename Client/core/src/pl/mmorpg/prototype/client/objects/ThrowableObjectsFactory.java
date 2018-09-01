package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.spells.ThrowableObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.ThrowableObjectCreationPacket;

public class ThrowableObjectsFactory extends ObjectsFactory
{
	public ThrowableObjectsFactory(PacketHandlerRegisterer registerer)
	{
		super(registerer);
	}

	public ThrowableObject produce(ThrowableObjectCreationPacket packet, CollisionMap<GameObject> linkedCollisionMap)
	{
		ThrowableObject object = (ThrowableObject) super.produce(packet, linkedCollisionMap);
		object.updateRotation(packet.targetData.x, packet.targetData.y);
		return object;
	}
}
