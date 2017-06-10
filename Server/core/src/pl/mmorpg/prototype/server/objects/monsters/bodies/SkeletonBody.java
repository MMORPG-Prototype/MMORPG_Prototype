package pl.mmorpg.prototype.server.objects.monsters.bodies;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.resources.Assets;

public class SkeletonBody extends MonsterBody
{

	public SkeletonBody(long id, int gold, Collection<Item> loot)
	{
		super(Assets.get("dead-skeleton.png"), id, gold, loot);
		setSize(32, 32);
	}

}
