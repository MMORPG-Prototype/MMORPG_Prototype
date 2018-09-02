package pl.mmorpg.prototype.server.collision.pixelmap;

import org.junit.jupiter.api.Test;
import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;

import static org.junit.jupiter.api.Assertions.*;

public class PixelCollisionMapTests
{

	@Test
	public void borderCollision()
	{
		PixelCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(4, 4);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject();
		collisionMap.placeObjectOnBorder(object);
		assertNotNull(collisionMap.getTopObject(0, 0));
		assertNotNull(collisionMap.getTopObject(0, 1));
		assertNotNull(collisionMap.getTopObject(0, 2));
		assertNotNull(collisionMap.getTopObject(0, 3));
		assertNotNull(collisionMap.getTopObject(3, 0));
		assertNotNull(collisionMap.getTopObject(3, 1));
		assertNotNull(collisionMap.getTopObject(3, 2));
		assertNotNull(collisionMap.getTopObject(3, 3));
		assertNotNull(collisionMap.getTopObject(1, 0));
		assertNotNull(collisionMap.getTopObject(2, 0));
		assertNotNull(collisionMap.getTopObject(1, 3));
		assertNotNull(collisionMap.getTopObject(2, 3));
		assertNull(collisionMap.getTopObject(1, 1));
		assertNull(collisionMap.getTopObject(1, 2));
		assertNull(collisionMap.getTopObject(2, 1));
		assertNull(collisionMap.getTopObject(2, 2));
	}

	@Test
	public void outOfBoundCollision()
	{
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(1000, 1000);
		assertNull(collisionMap.getTopObject(-1, 23));
		assertNull(collisionMap.getTopObject(234, -2));
		assertNull(collisionMap.getTopObject(-123, -1));
		assertNull(collisionMap.getTopObject(1000, 0));
		assertNull(collisionMap.getTopObject(0, 1000));
		assertNull(collisionMap.getTopObject(1234, 1234));
	}

	@Test
	public void successfulInsertionTest()
	{
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(1000, 1000);
		int objectWidth = 20;
		int objectHeight = 20;
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(25, 20, objectWidth, objectHeight);
		collisionMap.insert(object);
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

	private void assertThereIsPlacedObject(CollisionMap<PixelCollisionMapTestObject> collisionMap,
			PixelCollisionMapTestObject object)
	{
		for (int i = (int) object.getX(); i <= object.getWidth() + object.getX(); i++)
			for (int j = (int) object.getY(); j <= object.getY() + object.getHeight(); j++)
				assertEquals(collisionMap.getTopObject(i, j), object);
	}

	private void assertNullAroundObject(CollisionMap<PixelCollisionMapTestObject> collisionMap,
			PixelCollisionMapTestObject object)
	{
		for (int i = (int) object.getX() - 1; i <= object.getX() + object.getWidth() + 1; i++)
		{
			assertNull(collisionMap.getTopObject(i, (int) object.getY() - 1));
			assertNull(collisionMap.getTopObject(i, (int) (object.getY() + object.getHeight() + 1)));
		}
		for (int i = (int) object.getY(); i <= object.getY() + object.getHeight(); i++)
		{
			assertNull(collisionMap.getTopObject((int) object.getX() - 1, i));
			assertNull(collisionMap.getTopObject((int) (object.getX() + object.getWidth() + 1), i));
		}
	}

	@Test
	public void successfulMovement()
	{
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(100, 100);
		int objectWidth = 20;
		int objectHeight = 20;
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(25, 20, objectWidth, objectHeight);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.tryToRepositionGoingRight(moveValue, object);
		object.x += 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

}
