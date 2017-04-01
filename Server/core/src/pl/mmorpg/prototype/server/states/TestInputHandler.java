package pl.mmorpg.prototype.server.states;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.input.InputProcessorAdapter;
import pl.mmorpg.prototype.server.input.KeyHandler;
import pl.mmorpg.prototype.server.objects.TestObject;

public class TestInputHandler extends InputProcessorAdapter
{
	private TestObject object;
	private CollisionMap collisionMap;

	public TestInputHandler(TestObject object, CollisionMap collisionMap)
	{
		this.object = object;
		this.collisionMap = collisionMap;
	}

	public class WKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			object.moveUp(collisionMap);

		}

	}

	public class AKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			object.moveLeft(collisionMap);

		}

	}

	public class SKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			object.moveDown(collisionMap);

		}

	}

	public class DKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			object.moveRight(collisionMap);

		}

	}

	public class RKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			collisionMap.remove(object);

		}

	}

	public class SpaceKeyHandler implements KeyHandler
	{

		@Override
		public void handle()
		{
			collisionMap.insert(object);

		}

	}
}
