package pl.mmorpg.prototype.client.collision.pixelmap;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.collision.interfaces.ShiftableCollisionMap;

public class PixelCollisionMapTests
{
	@Test
	public void shiftLeftTest()
	{
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(1000, 1000);
		Rectangle startBounds = new Rectangle(10, 10, 10, 10);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		final int shiftX = 10;
		final int shiftY = 10;
		collisionMap.update(shiftX, shiftY);
		
		assertThereIsPlacedObject(collisionMap, shiftedRectangle(shiftX, shiftY, startBounds), object);
		assertNullAroundObject(collisionMap, object);
	}
	
	
	
	private Rectangle shiftedRectangle(int shiftX, int shiftY, Rectangle startBounds)
	{
		return new Rectangle(startBounds.x + shiftX, startBounds.y + shiftY, startBounds.width, startBounds.height);
	}



	@Test
	public void outOfBoundCollision()
	{
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(1000, 1000);
		assertThat(collisionMap.getObject(-1, 23)).isNull();
		assertThat(collisionMap.getObject(234, -2)).isNull();
		assertThat(collisionMap.getObject(-123, -1)).isNull();
		assertThat(collisionMap.getObject(1234, 1234)).isNull();
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
		assertThereIsPlacedObject(collisionMap, object.getCollisionRect(), object);
	}
	
	private void assertThereIsPlacedObject(CollisionMap<PixelCollisionMapTestObject> collisionMap, Rectangle bounds,
			PixelCollisionMapTestObject object)
	{
		for (int i = (int) bounds.getX(); i <= bounds.getWidth() + bounds.getX(); i++)
			for (int j = (int) bounds.getY(); j <= bounds.getY() + bounds.getHeight(); j++)
				assertThat(collisionMap.getObject(i, j)).isEqualTo(object);
	}
	
	private void assertNullAroundObject(CollisionMap<PixelCollisionMapTestObject> collisionMap, PixelCollisionMapTestObject object)
	{
		assertNullAroundObject(collisionMap, object.getCollisionRect());
	}

	private void assertNullAroundObject(CollisionMap<PixelCollisionMapTestObject> collisionMap, Rectangle bounds)
	{
		for (int i = (int) bounds.getX() - 1; i <= bounds.getX() + bounds.getWidth() + 1; i++)
		{
			assertThat(collisionMap.getObject(i, (int) bounds.getY() - 1)).isNull();
			assertThat(collisionMap.getObject(i, (int) (bounds.getY() + bounds.getHeight() + 1))).isNull();
		}
		for (int i = (int) bounds.getY(); i <= bounds.getY() + bounds.getHeight(); i++)
		{
			assertThat(collisionMap.getObject((int) bounds.getX() - 1, i)).isNull();
			assertThat(collisionMap.getObject((int) (bounds.getX() + bounds.getWidth() + 1), i)).isNull();
		}
	}

	@Test
	public void successfullMovement()
	{
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(100, 100);
		int objectWidth = 20;
		int objectHeight = 20;
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(25, 20, objectWidth, objectHeight);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.repositionGoingRight(moveValue, object);
		object.x += 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

}
