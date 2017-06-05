package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.SkeletonPropertiesBuilder;

public class Skeleton extends HealthBarMonster
{

	public Skeleton(long id)
	{
		super(Assets.get("skeletonWarrior.png"), 0, 0, id, new SkeletonPropertiesBuilder().build());

	}

}
