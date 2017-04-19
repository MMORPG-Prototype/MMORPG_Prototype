package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;

public class ThereIsNoSuchObjectToRemoveException extends RuntimeException
{

	public ThereIsNoSuchObjectToRemoveException(StackableCollisionObject object)
	{
		super("Object id: " + object.getUniqueId() + ", object: " + object);
	}

}
