package pl.mmorpg.prototype.server.packetshandling.characteractions;

import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;

interface MoveAction<T extends RectangleCollisionObject>
{
	void perform(MovableGameObject operationTarget, PixelCollisionMap<T> collisionMap);
	int getMoveDirection();
}