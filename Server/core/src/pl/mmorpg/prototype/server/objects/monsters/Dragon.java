package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class Dragon extends LootableMonster
{

	protected Dragon(Texture lookout, long id, MonsterProperties properties, PixelCollisionMap<GameObject> collisionMap,
			PlayState playState)
	{
		super(lookout, id, properties, collisionMap, playState);
	}


	@Override
	protected boolean shouldTargetOn(Monster monster)
	{
		return monster instanceof PlayerCharacter;
	}

}
