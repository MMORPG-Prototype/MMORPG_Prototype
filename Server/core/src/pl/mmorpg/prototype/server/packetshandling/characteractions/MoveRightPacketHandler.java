package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.server.states.PlayState;

public class MoveRightPacketHandler extends MovePacketHandler
{

	public MoveRightPacketHandler(Server server, PlayState playState)
	{
		super(server, playState);
	}

	@Override
	protected MoveAction getMoveAction()
	{
		return (gameObject, collisionMap) -> gameObject.moveRight(collisionMap);
	}

}
