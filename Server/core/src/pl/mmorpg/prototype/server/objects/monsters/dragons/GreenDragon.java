package pl.mmorpg.prototype.server.objects.monsters.dragons;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.monsters.properties.builders.GreenDragonPropertiesBuilder;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.abilities.TimedHealAbility;
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
		super(Assets.get("monster.png"), id, getDragonProperties(), collisionMap, playState);
		this.addAbility(new TimedHealAbility(5.0f, 10));
	}

	private static MonsterProperties getDragonProperties()
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
