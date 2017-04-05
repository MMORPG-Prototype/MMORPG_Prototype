package pl.mmorpg.prototype.server.packetshandling.characteractions;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;

@FunctionalInterface 
interface MoveAction
{
	void perform(MovableGameObject operationTarget, CollisionMap collisionMap);
}