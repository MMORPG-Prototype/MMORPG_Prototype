package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectsFactory
{

	public static GameObject produce(ObjectCreationPacket packet)
	{
		return produce(packet.identifier, packet.id, packet.x, packet.y);
	}

	public static GameObject produce(String identifier, long id, float x, float y)
    {
		GameObject object = null;
        if(identifier.compareTo(ObjectsIdentifier.getObjectIdentifier(Player.class)) == 0)
			object = new Player(id);

		if (object != null)
		{
			object.setPosition(x, y);
			return object;
		}
		throw new ObjectIdentifierNotFoundException(identifier);
    }
    
    private static class ObjectIdentifierNotFoundException extends GameException
    {  
		public ObjectIdentifierNotFoundException(String identifier)
		{
			super("Identifier: " + identifier);
		}
    }
}
