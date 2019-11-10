package pl.mmorpg.prototype.server.path.search.collisionDetectors;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.objects.GameObject;


public class SimpleCollisionDetector implements CollisionDetector
{
	private final CollisionMap<GameObject> collisionMap;
	private final GameObject gameObject;

	public SimpleCollisionDetector(CollisionMap<GameObject> collisionMap, GameObject gameObject)
	{
		this.collisionMap = collisionMap;
		this.gameObject = gameObject;
	}

	@Override
	public boolean areColliding(int x, int y)
	{
		return !(isEmptyOrSameObject(x - 2, y - 2)
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth() + 2, y - 2)
				&& isEmptyOrSameObject(x - 2, y + (int)gameObject.getHeight() + 2)
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth() + 2, y + (int)gameObject.getHeight() + 2)
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth()/2, y + (int)gameObject.getHeight()/2));
	}

	private boolean isEmptyOrSameObject(int x, int y)
	{
		RectangleCollisionObject object = collisionMap.getTopObject(x, y);
		return (object == null || object == this.gameObject) && collisionMap.isValidPoint(x, y);
	}

}
