package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.SnakePropertiesBuilder;

public class Snake extends HealthBarMonster
{

	public Snake(long id, CollisionMap<GameObject> collisionMap)
	{
		super(new TextureSheetAnimationInfo.Builder(Assets.get("snake.png"))
				.textureTileXOffset(0)
				.textureTileYOffset(0)
				.textureTileWidth(4)
				.textureTileHeight(4)
				.textureCountedTileWidth(4)
				.textureCountedTileHeight(4)
				.build(), 
				id, 
				new SnakePropertiesBuilder().build(), collisionMap);
		setSize(24, 24);
	}

}
