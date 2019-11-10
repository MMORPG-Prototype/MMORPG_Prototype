package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class LootableMonster extends AutoTargetingMonster
{
	private PlayState playState;

	protected LootableMonster(Texture lookout, long id, MonsterProperties properties, Rectangle walkingBounds,
			PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(lookout, id, properties, walkingBounds, collisionMap, playState);
		this.playState = playState;
	}
	
	@Override
	public void onRemoval()
	{
		super.onRemoval();
		MonsterBody deadBody = getDeadBodyWithLoot();
		deadBody.setPosition(getX(), getY());
		playState.addDeadBody(deadBody);
		playState.sendToAll(PacketsMaker.makeCreationPacket(deadBody));
	}
	
	protected abstract MonsterBody getDeadBodyWithLoot();

}
