package pl.mmorpg.prototype.server.collision.stackablemap;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionObjectsContainer;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.exceptions.DuplicateObjectIdException;
import pl.mmorpg.prototype.server.exceptions.ThereIsNoSuchObjectToRemoveException;


public class ObjectsContainer<T extends StackableCollisionObject> implements CollisionObjectsContainer<T>
{
	private Map<Integer, T> objects = new TreeMap<>();

	@Override
	public void insertObject(T object)
	{
		if(objects.containsKey(object.getUniqueId()))
			throw new DuplicateObjectIdException();
		objects.put(object.getUniqueId(), object);
	}

	@Override
	public void removeOrThrow(T object)
	{
		if(objects.remove(object.getUniqueId()) != object)
			throw new ThereIsNoSuchObjectToRemoveException(object); 
	}

	@Override
	public Collection<T> getObjects()
	{
		return objects.values();
	}
	
	@Override
	public boolean contains(T object)
	{
		return objects.containsKey(object.getUniqueId());
	}

}
