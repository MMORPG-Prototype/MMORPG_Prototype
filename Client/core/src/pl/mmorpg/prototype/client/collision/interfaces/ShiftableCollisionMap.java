package pl.mmorpg.prototype.client.collision.interfaces;

public interface ShiftableCollisionMap<T extends RectangleCollisionObject> extends CollisionMap<T>
{
	public void update(int shiftX, int shiftY);
}
