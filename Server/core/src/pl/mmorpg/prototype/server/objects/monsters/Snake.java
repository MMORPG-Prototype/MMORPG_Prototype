package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.monsters.properties.builders.SnakePropertiesBuilder;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.bodies.MonsterBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.SnakeBody;
import pl.mmorpg.prototype.server.objects.monsters.loot.MonsterLootGenerator;
import pl.mmorpg.prototype.server.objects.monsters.loot.SnakeLootGenerator;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class Snake extends LootableMonster
{
	public static final MonsterLootGenerator snakeLootGenerator = new SnakeLootGenerator();
	
	public Snake(long id, PixelCollisionMap<GameObject> collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, new SnakePropertiesBuilder().build(), collisionMap, playState);
	}

	@Override
	protected MonsterBody getDeadBodyWithLoot()
	{
		snakeLootGenerator.generateAndApplyLoot(this);
		Collection<Item> items = getItems();
		MonsterBody snakeBody = new SnakeBody(IdSupplier.getId(), getProperties().gold, items);
		return snakeBody;
	}

	@Override
	protected boolean shouldTargetOn(Monster monster)
	{
		return monster instanceof PlayerCharacter;
	}

}
