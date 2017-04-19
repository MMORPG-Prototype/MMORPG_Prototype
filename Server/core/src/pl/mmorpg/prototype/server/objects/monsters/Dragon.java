package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.clientservercommon.IdSupplier;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.DragonPropertiesBuilder;
import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.bodies.DragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.DragonLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class Dragon extends LootableMonster
{
	private static final MonsterLootGenerator dragonLootGenerator = new DragonLootGenerator();
	
	public Dragon(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, getDragonProperies(), collisionMap, playState);
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	

	public static MonsterProperties getDragonProperies()
	{
		return new DragonPropertiesBuilder().build();
	}

	@Override
	protected boolean shouldTargetOn(ItemsOwner monster)
	{
		return monster instanceof PlayerCharacter;
	}

	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		dragonLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		DragonBody dragonBody = new DragonBody(IdSupplier.getId(), getProperties().gold, items);
		return dragonBody;
	}
	

}
