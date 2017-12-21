package pl.mmorpg.prototype.client.collision.interfaces;

public interface ShiftableCollisionMap<T extends RectangleCollisionObject> extends CollisionMap<T>
{
	public void shiftLeft(int value);
	
	public void shiftRight(int value);
	
	public void shiftDown(int value);
	
	public void shiftUp(int value);
}
