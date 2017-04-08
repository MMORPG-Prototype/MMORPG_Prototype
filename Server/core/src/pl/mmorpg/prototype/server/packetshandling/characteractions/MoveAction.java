package pl.mmorpg.prototype.server.packetshandling.characteractions;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;

interface MoveAction
{
	void perform(MovableGameObject operationTarget, CollisionMap collisionMap);
	int getMoveDirection();
}