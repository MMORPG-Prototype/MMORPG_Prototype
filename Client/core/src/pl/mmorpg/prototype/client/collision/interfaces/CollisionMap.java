package pl.mmorpg.prototype.client.collision.interfaces;

public interface CollisionMap<T extends RectangleCollisionObject>
{
	void insert(T object);

	void remove(T object);
	
	T getObject(int gameX, int gameY);
	
	void repositionGoingRight(int moveValue, T object);
	
	void repositionGoingLeft(int moveValue, T object);
	
	void repositionGoingUp(int moveValue, T object);
	
	void repositionGoingDown(int moveValue, T object);
}
