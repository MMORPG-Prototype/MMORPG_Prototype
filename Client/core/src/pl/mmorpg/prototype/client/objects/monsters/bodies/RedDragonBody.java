package pl.mmorpg.prototype.client.objects.monsters.bodies;

import pl.mmorpg.prototype.client.resources.Assets;

public class RedDragonBody extends DeadBody
{
	public RedDragonBody(long id)
	{
		super(Assets.get("dead-dragon.png"), id);
	}

}
