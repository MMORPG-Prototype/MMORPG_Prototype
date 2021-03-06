package pl.mmorpg.prototype.server.objects.monsters.dragons;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.LootableMonster;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class Dragon extends LootableMonster
{

	protected Dragon(Texture lookout, long id, MonsterProperties properties, Rectangle walkingBounds, PixelCollisionMap<GameObject> collisionMap,
			PlayState playState)
	{
		super(lookout, id, properties, walkingBounds, collisionMap, playState);
	}


	@Override
	protected boolean shouldTargetOn(Monster monster)
	{
		return monster instanceof PlayerCharacter;
	}

}
