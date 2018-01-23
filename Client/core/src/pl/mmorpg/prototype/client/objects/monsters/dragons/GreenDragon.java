package pl.mmorpg.prototype.client.objects.monsters.dragons;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.monsters.HealthBarMonster;
import pl.mmorpg.prototype.client.objects.monsters.TextureSheetAnimationInfo;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.GreenDragonPropertiesBuilder;

public class GreenDragon extends HealthBarMonster
{
	public GreenDragon(long id, CollisionMap<GameObject> collisionMap, PacketHandlerRegisterer registerer)
	{
		super(new TextureSheetAnimationInfo
				.Builder(Assets.get("monsterSheet.png"))
				.textureTileWidth(12)
				.textureTileHeight(8)
				.textureCountedTileWidth(3)
				.textureCountedTileHeight(4)
				.textureTileXOffset(0)
				.textureTileYOffset(0)
				.build(), 
				id, 
				new GreenDragonPropertiesBuilder().build(), collisionMap, registerer);
	}

}
