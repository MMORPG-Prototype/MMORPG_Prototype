package pl.mmorpg.prototype.client.objects.monsters.bodies;

import pl.mmorpg.prototype.client.resources.Assets;

public class SnakeBody extends DeadBody
{
	public SnakeBody(long id)
	{
		super(Assets.get("dead-snake.png"), id);
	}
	
}
