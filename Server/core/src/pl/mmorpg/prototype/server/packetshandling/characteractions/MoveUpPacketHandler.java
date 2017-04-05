package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.server.states.PlayState;

public class MoveUpPacketHandler extends MovePacketHandler
{

	public MoveUpPacketHandler(Server server, PlayState playState)
	{
		super(server, playState);
	}

	@Override
	protected MoveAction getMoveAction()
	{
		return (gameObject, collisionMap) -> gameObject.moveUp(collisionMap);
	}

}
