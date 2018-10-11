package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.monsters.properties.builders.SkeletonPropertiesBuilder;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.SkeletonLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class Skeleton extends LootableMonster
{
	public static final MonsterLootGenerator skeletonLootGenerator = new SkeletonLootGenerator();
	
	public Skeleton(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, new SkeletonPropertiesBuilder().build(), collisionMap, playState);
	}

	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		skeletonLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		MonsterBody skeletonBody = new SkeletonBody(IdSupplier.getId(), getProperties().gold, items);
		return skeletonBody;
	}

	@Override
	protected boolean shouldTargetOn(Monster monster)
	{
		return monster instanceof PlayerCharacter;
	}

}
