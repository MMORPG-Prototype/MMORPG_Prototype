package pl.mmorpg.prototype.server.objects.containers.bodies;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.resources.Assets;

public class RedDragonBody extends MonsterBody
{
	public RedDragonBody(long id, int gold, Collection<Item> loot)
	{
		super(Assets.get("dead-dragon.png"), id, gold, loot);
	}

}
