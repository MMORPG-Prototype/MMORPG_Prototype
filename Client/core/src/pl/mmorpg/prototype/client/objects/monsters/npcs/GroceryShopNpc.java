package pl.mmorpg.prototype.client.objects.monsters.npcs;

import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo.Builder;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.NpcDefaultPropertiesBuilder;

public class GroceryShopNpc extends Monster
{
	public GroceryShopNpc(long id)
	{
		super(getSheetInfo(), id, new NpcDefaultPropertiesBuilder().build());
	}

	private static TextureSheetAnimationInfo getSheetInfo()
	{
		Builder builder = new TextureSheetAnimationInfo.Builder(Assets.get("characters.png"));
		return   builder
				.textureTileWidth(12)
				.textureTileHeight(8)
				.textureTileXOffset(3)
				.textureTileYOffset(0)
				.textureCountedTileWidth(3)
				.textureCountedTileHeight(4)
				.build();			
	}

}
