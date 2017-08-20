package pl.mmorpg.prototype.client.objects.monsters.bodies;

import pl.mmorpg.prototype.client.resources.Assets;

public class GrayDragonBody extends DeadBody
{
	public GrayDragonBody(long id)
	{
		super(Assets.get("dead-dragon.png"), id);
	}
	
}
