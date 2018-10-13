package pl.mmorpg.prototype.client.objects.monsters.dragons;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.HealthBarMonster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public class GrayDragon  extends HealthBarMonster
{

	public GrayDragon(long id, MonsterProperties properties, CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(new TextureSheetAnimationInfo
				.Builder(Assets.get("monsterSheet.png"))
				.textureTileWidth(12)
				.textureTileHeight(8)
				.textureCountedTileWidth(3)
				.textureCountedTileHeight(4)
				.textureTileXOffset(3)
				.textureTileYOffset(4)
				.build(), 
				id,
				properties, collisionMap, registerer);
	}

}
