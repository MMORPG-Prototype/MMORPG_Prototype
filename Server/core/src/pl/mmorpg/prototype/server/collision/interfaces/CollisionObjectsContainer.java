package pl.mmorpg.prototype.server.collision.interfaces;

import java.util.Collection;

public interface CollisionObjectsContainer<T extends StackableCollisionObject>
{
	void insertObject(T object);
	
	void removeOrThrow(T object);

	boolean contains(T object);
	
	Collection<T> getObjects();
	
	T top();
	
	T removeFromTop();
	
}
