package pl.mmorpg.prototype.server.packetshandling.characteractions;

import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;

interface MoveAction
{
	void perform(MovableGameObject operationTarget, PixelCollisionMap collisionMap);
	int getMoveDirection();
}