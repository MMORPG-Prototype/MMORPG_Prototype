package pl.mmorpg.prototype.server.collision.interfaces;

import java.awt.Point;
import java.util.Collection;

public interface StackableCollisionMap<T extends StackableCollisionObject> extends CollisionMap<T>
{
	Collection<T> getCollisionObjectsFromSingleContainer(Point position);
	
	Collection<T> getCollidingObjects(T object);

	void moveRight(T object);
	
	void moveLeft(T object);
	
	void moveDown(T object);
	
	void moveUp(T object);
	
	@Override
	default T tryToRepositionGoingRight(int moveValue, T object)
	{
		Point position = new Point(object.getCenter());
		for(int i=0; i<moveValue; i++)
		{	
			position.x++;
			T collision = getTopObject(position.x, position.y);
			if(collision != null)
			{
				moveRight(object);
				return collision;
			}
		}
		return null;
	}
	

	@Override
	default T tryToRepositionGoingLeft(int moveValue, T object)
	{
		Point position = new Point(object.getCenter());
		for(int i=0; i<moveValue; i++)
		{	
			position.x--;
			T collision = getTopObject(position.x, position.y);
			if(collision != null)
			{
				moveLeft(object);
				return collision;
			}
		}
		return null;
	}

	@Override
	default T tryToRepositionGoingUp(int moveValue, T object)
	{
		Point position = new Point(object.getCenter());
		for(int i=0; i<moveValue; i++)
		{	
			position.y--;
			T collision = getTopObject(position.x, position.y);
			if(collision != null)
			{
				moveUp(object);
				return collision;
			}
		}
		return null;
	}

	@Override
	default T tryToRepositionGoingDown(int moveValue, T object)
	{
		Point position = new Point(object.getCenter());
		for(int i=0; i<moveValue; i++)
		{	
			position.y++;
			T collision = getTopObject(position.x, position.y);
			if(collision != null)
			{
				moveDown(object);
				return collision;
			}
		}
		return null;
	}
}
