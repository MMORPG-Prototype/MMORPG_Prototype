package pl.mmorpg.prototype.client.path.search.collisionDetectors;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.client.objects.GameObject;

public class GameObjectCollisionDetector implements CollisionDetector
{
	private CollisionMap<GameObject> collisionMap;
	private GameObject gameObject;

	public GameObjectCollisionDetector(CollisionMap<GameObject> collisionMap, GameObject gameObject)
	{
		this.collisionMap = collisionMap;
		this.gameObject = gameObject;
	}

	@Override
	public boolean isColliding(int x, int y)
	{
		return !(isEmptyOrSameObject(x, y) 
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth(), y)
				&& isEmptyOrSameObject(x, y + (int)gameObject.getHeight()) 
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth(), y + (int)gameObject.getHeight()));
	}
	
	private boolean isEmptyOrSameObject(int x, int y)
	{
		RectangleCollisionObject object = collisionMap.getObject(x, y);
		return (object == null || object == this.gameObject) && collisionMap.isValidPoint(x, y);
	}

}
