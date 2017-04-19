package pl.mmorpg.prototype.server.collision.interfaces;

import java.awt.Point;

public interface StackableCollisionObject extends RectangleCollisionObject
{
	Point getCenter();

	int getCollisionContainerId();
	
	void setCollisionContainerId(int id);

	boolean isColliding(RectangleCollisionObject object);
}
