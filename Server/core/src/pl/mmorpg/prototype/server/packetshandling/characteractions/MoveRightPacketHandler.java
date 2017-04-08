package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class MoveRightPacketHandler extends MovePacketHandler
{

	public MoveRightPacketHandler(Server server, PlayState playState)
	{
		super(server, playState);
	}

	@Override
	public void perform(MovableGameObject operationTarget, CollisionMap collisionMap)
	{
		operationTarget.moveRight(collisionMap);
	}

	@Override
	public int getMoveDirection()
	{
		return Directions.RIGHT;
	}

}
