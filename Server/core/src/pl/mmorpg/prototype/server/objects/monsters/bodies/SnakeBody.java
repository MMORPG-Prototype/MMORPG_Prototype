package pl.mmorpg.prototype.server.objects.monsters.bodies;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.resources.Assets;

public class SnakeBody extends MonsterBody
{

	public SnakeBody(long id, int gold, Collection<Item> loot)
	{
		super(Assets.get("dead-snake.png"), id, gold, loot);
	}
	
}
