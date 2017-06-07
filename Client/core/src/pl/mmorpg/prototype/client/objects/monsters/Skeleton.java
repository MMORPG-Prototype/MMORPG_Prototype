package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.SkeletonPropertiesBuilder;

public class Skeleton extends HealthBarMonster
{

	public Skeleton(long id)
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
				new SkeletonPropertiesBuilder().build());

	}

}
