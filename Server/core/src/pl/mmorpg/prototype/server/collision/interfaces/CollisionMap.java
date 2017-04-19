package pl.mmorpg.prototype.server.collision.interfaces;

public interface CollisionMap<T extends RectangleCollisionObject>
{
	void insert(T object);

	void remove(T object);
	
	T get(int gameX, int gameY);
	
	T tryToRepositionGoingRight(int moveValue, T object);
	
	T tryToRepositionGoingLeft(int moveValue, T object);
	
	T tryToRepositionGoingUp(int moveValue, T object);
	
	T tryToRepositionGoingDown(int moveValue, T object);
}
