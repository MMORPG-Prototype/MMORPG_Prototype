package pl.mmorpg.prototype.client.objects.monsters.bodies;

import pl.mmorpg.prototype.client.resources.Assets;

public class GreenDragonBody extends DeadBody
{
	public GreenDragonBody(long id)
	{
		super(Assets.get("dead-dragon.png"), id);
	}
	
}
