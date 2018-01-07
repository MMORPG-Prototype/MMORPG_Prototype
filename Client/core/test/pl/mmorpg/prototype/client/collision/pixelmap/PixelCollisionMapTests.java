package pl.mmorpg.prototype.client.collision.pixelmap;

import static com.google.common.truth.Truth.assertThat;

import java.awt.Point;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.collision.interfaces.ShiftableCollisionMap;

public class PixelCollisionMapTests
{
	@Test 
	public void whenShiftedAndObjectsMovesRightAndUpCollisionShouldBeRepositioned()
	{
		int width = 10;
		int height = 10;
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		Rectangle startBounds = new Rectangle(0, 0, 2, 2);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		int shiftX = 2;
		int shiftY = 2;
		collisionMap.update(shiftX, shiftY);
		print(width, height, collisionMap);
		collisionMap.repositionGoingRight(2, object);
		object.x += 2;
		collisionMap.repositionGoingUp(2, object);
		object.y += 2;
		System.out.println();
		printCollisionMap(collisionMap, shiftX, shiftY, width, height);
		assertNullAroundObject(collisionMap, object);
		assertThereIsPlacedObject(collisionMap, object);
	}
	
	@Test
	public void whenObjectStepsOutOfRightBoundShouldBeRemoved()
	{
		int width = 10;
		int height = 10;
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		Rectangle startBounds = new Rectangle(8, 8, 2, 2);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		collisionMap.repositionGoingRight(1, object);
		assertNullEverywhere(collisionMap, width, height);
	}
	
	@Test
	public void whenObjectIsOutOfLeftBoundAndStepsIntoValidFieldShouldBeInserted()
	{
		int width = 10;
		int height = 10;
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		Rectangle startBounds = new Rectangle(-1, 0, 2, 2);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		collisionMap.repositionGoingRight(1, object);
		object.x += 1;
		assertNullAroundObject(collisionMap, object);
		assertThereIsPlacedObject(collisionMap, object);
	}
	
	@Test
	public void whenObjectIsOutOfLeftBoundAndStepsIntoInvalidFieldShouldNotBeInsterted()
	{
		int width = 10;
		int height = 10;
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		Rectangle startBounds = new Rectangle(-3, 0, 2, 2);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		collisionMap.repositionGoingRight(2, object);
		assertNullEverywhere(collisionMap, width, height);
	}

	private void assertNullEverywhere(ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap, int width, int height)
	{
		for (int i = 0; i < width; i++)
			for (int j = 0; j <  height; j++)
				assertThat(collisionMap.getObject(i, j)).isNull();
	}

	@Test
	public void shiftRight()
	{
		shiftTest(2, 0);
	}

	@Test
	public void shiftLeft()
	{
		shiftTest(-2, 0);
	}

	@Test
	public void shiftDown()
	{
		shiftTest(0, -2);
	}

	@Test
	public void shiftUp()
	{
		shiftTest(0, 2);
	}

	@Test
	public void shiftUpAndRight()
	{
		shiftTest(2, 2);
	}

	@Test
	public void shiftUpAndLeft()
	{
		shiftTest(-2, 2);
	}

	@Test
	public void shiftDownAndRight()
	{
		shiftTest(2, -2);
	}

	@Test
	public void shiftDownAndLeft()
	{
		shiftTest(-2, -2);
	}

	private void shiftTest(final int shiftX, final int shiftY)
	{
		int width = 10;
		int height = 10;
		ShiftableCollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		Rectangle startBounds = new Rectangle(5, 5, 2, 2);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(new Rectangle(startBounds));
		collisionMap.insert(object);
		collisionMap.update(shiftX, shiftY);
		assertNullAroundObject(collisionMap, startBounds);
		assertThereIsPlacedObject(collisionMap, startBounds, object);
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
	public void successfulInsertion()
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
		for (int i = (int) bounds.getX(); i < bounds.getWidth() + bounds.getX(); i++)
			for (int j = (int) bounds.getY(); j < bounds.getY() + bounds.getHeight(); j++)
				assertThat(collisionMap.getObject(i, j)).isEqualTo(object);
	}

	private void assertNullAroundObject(CollisionMap<PixelCollisionMapTestObject> collisionMap,
			PixelCollisionMapTestObject object)
	{
		assertNullAroundObject(collisionMap, object.getCollisionRect());
	}

	private void assertNullAroundObject(CollisionMap<PixelCollisionMapTestObject> collisionMap, Rectangle bounds)
	{
		for (int i = (int) bounds.getX() - 1; i <= bounds.getX() + bounds.getWidth(); i++)
		{
			assertThat(collisionMap.getObject(i, (int) bounds.getY() - 1)).isNull();
			assertThat(collisionMap.getObject(i, (int) (bounds.getY() + bounds.getHeight()))).isNull();
		}
		for (int i = (int) bounds.getY(); i <= bounds.getY() + bounds.getHeight(); i++)
		{
			assertThat(collisionMap.getObject((int) bounds.getX() - 1, i)).isNull();
			assertThat(collisionMap.getObject((int) (bounds.getX() + bounds.getWidth()), i)).isNull();
		}
	}

	@Test
	public void repositionObjectRight()
	{
		int width = 10;
		int height = 10;
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		int objectWidth = 2;
		int objectHeight = 2;
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(0, 0, objectWidth, objectHeight);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.repositionGoingRight(moveValue, object);
		object.x += 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

	@Test
	public void repositionObjectLeft()
	{
		int width = 10;
		int height = 10;
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(8, 8, 2, 2);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.repositionGoingLeft(moveValue, object);
		object.x -= 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

	@Test
	public void repositionObjectDown()
	{
		int width = 10;
		int height = 10;
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(8, 8, 2, 2);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.repositionGoingDown(moveValue, object);
		object.y -= 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

	@Test
	public void repositionObjectUp()
	{
		int width = 9;
		int height = 9;
		CollisionMap<PixelCollisionMapTestObject> collisionMap = new PixelCollisionMap<>(width, height);
		PixelCollisionMapTestObject object = new PixelCollisionMapTestObject(0, 0, 5, 5);
		collisionMap.insert(object);
		int moveValue = 2;
		collisionMap.repositionGoingUp(moveValue, object);
		object.y += 2;
		assertThereIsPlacedObject(collisionMap, object);
		assertNullAroundObject(collisionMap, object);
	}

	private void printCollisionMap(CollisionMap<PixelCollisionMapTestObject> collisionMap, int shiftX, int shiftY,
			int width, int height)
	{
		System.out.println();
		for (int i = height - 1; i >= 0; i--)
		{
			for (int j = 0; j < width; j++)
				if (collisionMap.getObject(j + shiftX, i + shiftY) != null)
					System.out.print("1 ");
				else
					System.out.print("0 ");
			System.out.println();
		}

	}

	private Rectangle getFirstObjectBounds(CollisionMap<PixelCollisionMapTestObject> collisionMap, int shiftX,
			int shiftY, int width, int height)
	{
		Point collision = getLowerLeftObjectCollision(collisionMap, shiftX, shiftY, width, height);
		if (collision == null)
			return null;
		Rectangle rectangle = new Rectangle(collision.x, collision.y, 0, 0);
		while (collisionMap.getObject(collision.x + 1, collision.y) != null)
		{
			collision.x++;
			rectangle.width++;
		}
		while (collisionMap.getObject(collision.x, collision.y + 1) != null)
		{
			collision.y++;
			rectangle.height++;
		}
		return rectangle;
	}

	private Point getLowerLeftObjectCollision(CollisionMap<PixelCollisionMapTestObject> collisionMap, int shiftX,
			int shiftY, int width, int height)
	{
		for (int i = shiftX; i < shiftX + width; i++)
			for (int j = shiftY; j < shiftY + height; j++)
				if (collisionMap.getObject(i, j) != null)
					return new Point(i, j);
		return null;
	}

	private void print(int width, int height, CollisionMap<PixelCollisionMapTestObject> collisionMap)
	{
		System.out.println(getFirstObjectBounds(collisionMap, 0, 0, width, height));
		printCollisionMap(collisionMap, 0, 0, width, height);
	}

}
