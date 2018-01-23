package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.SkeletonPropertiesBuilder;

public class Skeleton extends HealthBarMonster
{

	public Skeleton(long id, CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(new TextureSheetAnimationInfo
				.Builder(Assets.get("skeletonWarrior.png"))
				.textureTileHeight(4)
				.textureTileWidth(4)
				.textureCountedTileWidth(4)
				.textureCountedTileHeight(4)
				.textureTileXOffset(0)
				.textureTileYOffset(0)
				.build(), 
				id, 
				new SkeletonPropertiesBuilder().build(), collisionMap, registerer);

	}

}
