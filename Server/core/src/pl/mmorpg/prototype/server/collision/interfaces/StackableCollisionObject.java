package pl.mmorpg.prototype.server.collision.interfaces;

import java.awt.Point;

public interface StackableCollisionObject extends RectangleCollisionObject
{
	Point getCenter();

	int getUniqueId();

	boolean isColliding(RectangleCollisionObject object);
}
