package pl.mmorpg.prototype.server.objects.monsters.dragons;

import java.util.Collection;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.server.objects.monsters.properties.builders.GrayDragonPropertiesBuilder;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.bodies.GrayDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.GreenDragonLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class GrayDragon extends Dragon
{
	private static final MonsterLootGenerator dragonLootGenerator = new GreenDragonLootGenerator();

	public GrayDragon(long id, Rectangle walkingBounds,
			PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, getDragonProperies(), walkingBounds, collisionMap, playState);
	}
	
	private static MonsterProperties getDragonProperies()
	{
		return new GrayDragonPropertiesBuilder().build();
	}

	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		dragonLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		GrayDragonBody dragonBody = new GrayDragonBody(IdSupplier.getId(), getProperties().gold, items);
		return dragonBody;
	}

}
