package pl.mmorpg.prototype.client.objects.monsters.npcs;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo.Builder;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.NpcNames;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public class GroceryShopNpc extends Npc implements Shop
{
	public GroceryShopNpc(long id, MonsterProperties properties,
			CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(NpcNames.GROCERY_NPC, getSheetInfo(), id, properties, collisionMap, registerer);
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
