package pl.mmorpg.prototype.client.objects.monsters.bodies;

import pl.mmorpg.prototype.client.resources.Assets;

public class SkeletonBody extends DeadBody
{
	public SkeletonBody(long id)
	{
		super(Assets.get("dead-skeleton.png"), id);
		setSize(32, 32);
	}

}
