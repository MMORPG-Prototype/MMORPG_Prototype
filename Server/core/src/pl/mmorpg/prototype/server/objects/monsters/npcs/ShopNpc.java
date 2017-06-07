package pl.mmorpg.prototype.server.objects.monsters.npcs;

import java.util.Collection;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.WalkingMonster;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class ShopNpc extends WalkingMonster
{
	private Collection<ShopItemWrapper> availableItems;

	public ShopNpc(Texture lookout, long id, MonsterProperties properties, PixelCollisionMap<GameObject> collisionMap,
			PlayState playState, Collection<ShopItemWrapper> availableItems)
	{
		super(Assets.get("MainChar.png"), id, properties, collisionMap, playState);
		this.availableItems = availableItems;
	}
	
	public Collection<ShopItemWrapper> getAvailableItems()
	{
		return availableItems;
	}

}
