package pl.mmorpg.prototype.server.collision.stackablemap;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionObjectsContainer;
import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.exceptions.OutOfBoundsException;


public class LayerCollisionMap<T extends StackableCollisionObject> implements StackableCollisionMap<T>
{
	private CollisionObjectsContainer<T>[][] collisionMap;

	public LayerCollisionMap(int width, int height, int containerWidth, int containerHeight)
	{
		collisionMap = createCollisionMap(width, height); 
	}

	private CollisionObjectsContainer<T>[][] createCollisionMap(int width, int height)
	{  
		CollisionObjectsContainer<T>[][] collisionMap = new ObjectsContainer[width][];
		for (int i = 0; i < width; i++)
		{
			collisionMap[i] = new ObjectsContainer[height];
			for (int j = 0; j < height; j++)
				collisionMap[i][j] = new ObjectsContainer<T>();
		} 
		return collisionMap;
	}

	@Override
	public void insert(T object)
	{
		Point position = object.getCenter();
		getContainer(position).insertObject(object);
	}

	private CollisionObjectsContainer<T> getContainer(Point position)
	{
		return collisionMap[position.x][position.y];
	}

	@Override
	public Collection<T> getCollisionObjects(Point position)
	{
		Collection<T> collisionObjects = getContainer(position).getObjects();
		return collisionObjects;
	}

	@Override
	public void remove(T object)
	{
		Point position = object.getCenter();
		getContainer(position).removeOrThrow(object);
	}

	@Override
	public void moveRight(T object)
	{
		Point position = new Point(object.getCenter());
		CollisionObjectsContainer<T> container = getContainer(position);

		container.removeOrThrow(object);
		position.x++;
		CollisionObjectsContainer<T> newFirstLayerContainer = getContainer(position);
		newFirstLayerContainer.insertObject(object);
	}

	@Override
	public void moveLeft(T object)
	{
		Point position = new Point(object.getCenter());
		CollisionObjectsContainer<T> container = getContainer(position);

		container.removeOrThrow(object);
		position.x--;
		CollisionObjectsContainer<T> newFirstLayerContainer = getContainer(position);
		newFirstLayerContainer.insertObject(object);

	}

	@Override
	public void moveDown(T object)
	{
		Point position = new Point(object.getCenter());
		if (position.y + 1 >= collisionMap[0].length)
			throw new OutOfBoundsException(new Point(position.x, position.y + 1));
		CollisionObjectsContainer<T> container = getContainer(position);

		container.removeOrThrow(object);
		position.y++;
		CollisionObjectsContainer<T> newFirstLayerContainer = getContainer(position);
		newFirstLayerContainer.insertObject(object);

	}

	@Override
	public void moveUp(T object)
	{
		Point position = new Point(object.getCenter());
		CollisionObjectsContainer<T> container = getContainer(position);

		container.removeOrThrow(object);
		position.y--;
		CollisionObjectsContainer<T> newFirstLayerContainer = getContainer(position);
		newFirstLayerContainer.insertObject(object);

	}

	@Override
	public Collection<T> getCollidingObjects(T object)
	{
		Point position = new Point(object.getCenter());
		Rectangle containerIndexRange = new Rectangle(position.x, position.y, 1, 1);
		if(containerIndexRange.x > 0)
		{
			containerIndexRange.width++;
			containerIndexRange.x--;
		}
		if(containerIndexRange.x < collisionMap.length - 1)
			containerIndexRange.width++;
		if(containerIndexRange.y > 0)
		{
			containerIndexRange.y--;
			containerIndexRange.height++;
		}
		if(containerIndexRange.y < collisionMap[0].length - 1)
			containerIndexRange.height++;
		
		return getCollidingObjects(object, containerIndexRange);
	}

	private Collection<T> getCollidingObjects(T object, Rectangle containerIndexRange)
	{
		Collection<T> collisionObjects = new LinkedList<>();
		for(int i=containerIndexRange.x; i < containerIndexRange.x + containerIndexRange.width; i++)
			for(int j=containerIndexRange.y; j < containerIndexRange.y + containerIndexRange.height; j++)
			{
				CollisionObjectsContainer<T> targetContainer = getContainer(new Point(i, j));
				Collection<T> collidingObjects = getCollidingObjects(object, targetContainer);
				collisionObjects.addAll(collidingObjects);
			}
		return collisionObjects;
	}

	private Collection<T> getCollidingObjects(RectangleCollisionObject object,
			CollisionObjectsContainer<T> targetContainer)
	{
		return targetContainer
				.getObjects()
				.stream()
				.filter( possibleCollision -> object != possibleCollision 
											&& possibleCollision.isColliding(object))
				.collect(Collectors.toList());
	}
	
	/*public void printContent()
	{
		for(int i=0; i<collisionMap.length; i++)
			for(int j=0; j<collisionMap[0].length; j++)
			{
				Point position = new Point(i, j);
				CollisionObjectsContainer container = getContainer(position);
				Collection<RectangleCollisionObject> objects = container.getObjects();
				if(!objects.isEmpty())
					System.out.println(position + " " + objects);
			}
	}*/

}
