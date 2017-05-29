package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.GreenDragonPropertiesBuilder;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.GreenDragonLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class GreenDragon extends Dragon
{
	private static final MonsterLootGenerator dragonLootGenerator = new GreenDragonLootGenerator();
	
	public GreenDragon(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, getDragonProperies(), collisionMap, playState);
	}

	public static MonsterProperties getDragonProperies()
	{
		return new GreenDragonPropertiesBuilder().build();
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}


	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		dragonLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		GreenDragonBody dragonBody = new GreenDragonBody(IdSupplier.getId(), getProperties().gold, items);
		return dragonBody;
	}
	

}