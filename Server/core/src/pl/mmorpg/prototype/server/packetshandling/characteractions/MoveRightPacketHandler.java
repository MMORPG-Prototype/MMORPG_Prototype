package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.badlogic.gdx.Gdx;

import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class MoveRightPacketHandler extends MovePacketHandler
{

	public MoveRightPacketHandler(PlayState playState)
	{
		super(playState);
	}

	@Override
	public void perform(MovableGameObject operationTarget, PixelCollisionMap<GameObject> collisionMap)
	{
		operationTarget.moveRight(collisionMap, Gdx.graphics.getDeltaTime());
	}

}
