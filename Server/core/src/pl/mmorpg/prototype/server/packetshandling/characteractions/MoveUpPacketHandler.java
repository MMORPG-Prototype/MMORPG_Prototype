package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class MoveUpPacketHandler extends MovePacketHandler
{

	public MoveUpPacketHandler(Server server, PlayState playState)
	{
		super(server, playState);
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
