package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.client.objects.monsters.Dragon;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectsFactory
{
	public GameObject produce(ObjectCreationPacket packet)
	{
		return produce(packet.identifier, packet.id, packet.x, packet.y);
	}

	public GameObject produce(String identifier, long id, float x, float y)
    {
		GameObject object = null;
        if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(Player.class)))
			object = new Player(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(Dragon.class)))
        	object = new Dragon(id);
        else
    		throw new ObjectIdentifierNotFoundException(identifier);
        	
		object.setPosition(x, y); 
		if(object instanceof MovableGameObject)
			((MovableGameObject)object).initPosition(x, y);
		return object;
    }
    
    private static class ObjectIdentifierNotFoundException extends GameException
    {  
		public ObjectIdentifierNotFoundException(String identifier)
		{
			super("Identifier: " + identifier);
		}
    }
}
