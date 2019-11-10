package pl.mmorpg.prototype.server.path.search.collisionDetectors;

import java.awt.Rectangle;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;

public class RestrictedAreaGameObjectCollisionDetector implements CollisionDetector
{
	private final static float RESTRICTED_AREA_DISTANCE = 2000.0f;
	private final SimpleCollisionDetector simpleCollisionDetector;
	private final Rectangle restrictedArea;

	public RestrictedAreaGameObjectCollisionDetector(CollisionMap<GameObject> collisionMap, GameObject gameObject)
	{
		simpleCollisionDetector = new SimpleCollisionDetector(collisionMap, gameObject);
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
	public boolean areColliding(int x, int y)
	{
		return !isInRestrictedArea(x, y) || simpleCollisionDetector.areColliding(x, y);
	}
	
	private boolean isInRestrictedArea(int x, int y)
	{
		return restrictedArea.contains(x, y);
	}

}
