package pl.mmorpg.prototype.server.collision.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface RectangleCollisionObject
{
	Rectangle getCollisionRect();

	default boolean containsPoint(int gameX, int gameY)
	{
		return getCollisionRect().contains(gameX, gameY);
	}
}
