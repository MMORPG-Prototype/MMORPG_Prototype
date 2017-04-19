package pl.mmorpg.prototype.server.collision.stackablemap;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.TreeMap;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionObjectsContainer;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.exceptions.DuplicateObjectException;
import pl.mmorpg.prototype.server.exceptions.ThereIsNoSuchObjectToRemoveException;

public class ObjectsContainer<T extends StackableCollisionObject> implements CollisionObjectsContainer<T>
{
	private int nextCollisionId = 0;
	private TreeMap<Integer, T> objects;

	public ObjectsContainer()
	{
		objects = new TreeMap<>();
	}

	@Override
	public void insertObject(T object)
	{
		if (objects.containsKey(object.getCollisionContainerId())
				&& objects.get(object.getCollisionContainerId()) == object)
			throw new DuplicateObjectException();
		object.setCollisionContainerId(nextCollisionId);
		nextCollisionId++;
		objects.put(object.getCollisionContainerId(), object);
	}

	@Override
	public void removeOrThrow(T object)
	{
		if (objects.remove(object.getCollisionContainerId()) != object)
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
		return objects.containsKey(object.getCollisionContainerId());
	}

	@Override
	public T top()
	{
		return objects.lastEntry().getValue();
	}

	@Override
	public T removeFromTop()
	{
		return objects.pollLastEntry().getValue();
	}
	
	@Override
	public T top(int gameX, int gameY)
	{
		Entry<Integer, T> topEntry = objects.lastEntry();
		while(topEntry != null)
		{
			T object = topEntry.getValue();
			if(object.containsPoint(gameX, gameY))
				return object;
			topEntry = objects.lowerEntry(topEntry.getKey());
		}
		return null;
	}

}
