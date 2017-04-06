package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.resources.Assets;

public class TestObject extends MovableGameObject
{
	private CollisionMap collisionMap;

	public TestObject(CollisionMap collisionMap, long id)
	{
		super(Assets.get("ball.png"), id);
		this.collisionMap = collisionMap;
		setPosition(100.0f, 100.0f);
	}


	@Override
	public void update(float deltaTime)
	{
	}

}
