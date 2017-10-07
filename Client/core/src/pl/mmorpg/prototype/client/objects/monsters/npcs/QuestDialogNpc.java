package pl.mmorpg.prototype.client.objects.monsters.npcs;

import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo.Builder;
import pl.mmorpg.prototype.client.resources.Assets;

public class QuestDialogNpc extends Npc
{

	public QuestDialogNpc(long id)
	{
		super(getSheetInfo(), id);
	}
	
	private static TextureSheetAnimationInfo getSheetInfo()
	{
		Builder builder = new TextureSheetAnimationInfo.Builder(Assets.get("characters.png"));
		return   builder
				.textureTileWidth(12)
				.textureTileHeight(8)
				.textureTileXOffset(0)
				.textureTileYOffset(4)
				.textureCountedTileWidth(3)
				.textureCountedTileHeight(4)
				.build();			
	}

}
