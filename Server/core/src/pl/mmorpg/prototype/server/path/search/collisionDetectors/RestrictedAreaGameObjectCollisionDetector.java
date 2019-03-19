package pl.mmorpg.prototype.server.path.search.collisionDetectors;

import java.awt.Rectangle;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.objects.GameObject;

public class RestrictedAreaGameObjectCollisionDetector implements CollisionDetector
{
	private final static float RESTRICTED_AREA_DISTANCE = 200.0f;
	private final CollisionMap<GameObject> collisionMap;
	private final GameObject gameObject;
	private final Rectangle restrictedArea;

	public RestrictedAreaGameObjectCollisionDetector(CollisionMap<GameObject> collisionMap, GameObject gameObject)
	{
		this.collisionMap = collisionMap;
		this.gameObject = gameObject;
		this.restrictedArea = createRestrictedAreaRectangle(gameObject.getX(), gameObject.getY());
	}

	private Rectangle createRestrictedAreaRectangle(float initialX, float initialY)
	{
		return new Rectangle((int)initialX-(int)RESTRICTED_AREA_DISTANCE/2, 
				(int)initialY-(int)RESTRICTED_AREA_DISTANCE/2, 
				(int)RESTRICTED_AREA_DISTANCE, 
				(int)RESTRICTED_AREA_DISTANCE);
	}

	@Override
	public boolean isColliding(int x, int y)
	{
		return !isInRestrictedArea(x, y) || !(isEmptyOrSameObject(x, y) 
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth(), y)
				&& isEmptyOrSameObject(x, y + (int)gameObject.getHeight()) 
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth(), y + (int)gameObject.getHeight())
				&& isEmptyOrSameObject(x + (int)gameObject.getWidth()/2, y + (int)gameObject.getHeight()/2));
	}
	
	private boolean isInRestrictedArea(int x, int y)
	{
		return restrictedArea.contains(x, y);
	}

	private boolean isEmptyOrSameObject(int x, int y)
	{
		RectangleCollisionObject object = collisionMap.getTopObject(x, y);
		return (object == null || object == this.gameObject) && collisionMap.isValidPoint(x, y);
	}

}
