package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.RedDragonPropertiesBuilder;

public class RedDragon extends HealthBarMonster
{
	public RedDragon(long id)
	{
		super(new TextureSheetAnimationInfo
				.Builder(Assets.get("monsterSheet.png"))
				.textureTileWidth(12)
				.textureTileHeight(8)
				.textureCountedTileWidth(3)
				.textureCountedTileHeight(4)
				.textureTileXOffset(9)
				.textureTileYOffset(0)
				.build(), 
				id, 
				new RedDragonPropertiesBuilder().build());
	}
}
