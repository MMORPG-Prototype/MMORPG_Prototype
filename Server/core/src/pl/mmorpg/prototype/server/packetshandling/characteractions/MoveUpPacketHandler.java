package pl.mmorpg.prototype.server.packetshandling.characteractions;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class MoveUpPacketHandler extends MovePacketHandler
{

	public MoveUpPacketHandler(PlayState playState)
	{
		super(playState);
	}
	
	@Override
	public void perform(MovableGameObject operationTarget, CollisionMap collisionMap)
	{
		operationTarget.moveUp(collisionMap);		
	}

	@Override
	public int getMoveDirection()
	{
		return Directions.UP;
	}

}
