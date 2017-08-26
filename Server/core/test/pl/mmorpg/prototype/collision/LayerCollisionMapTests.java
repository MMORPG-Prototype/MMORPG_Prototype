package pl.mmorpg.prototype.collision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionMap;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.collision.stackablemap.LayerCollisionMap;
import pl.mmorpg.prototype.server.exceptions.OutOfBoundsException;

@RunWith(MockitoJUnitRunner.class)
public class LayerCollisionMapTests
{
	private StackableCollisionMap<StackableCollisionObject> collisionMap;
	private static final int containerWidth = 10;
	private static final int containerHeight = 10;
	
	@Mock
	private StackableCollisionObject object;

	@Before
	public void createCollisionMap()
	{
		collisionMap = new LayerCollisionMap<>(6, 6, containerWidth, containerHeight);
	}

	@Test
	public void insertObjectOnPosition0_0_shouldBeAbleToRetrieveIt()
	{
		instertObjectOnPoistion_shouldBeAbleToRetreiveIt(calcPosition(0, 0));
	}

	private void instertObjectOnPoistion_shouldBeAbleToRetreiveIt(Point position)
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(position);
		collisionMap.insert(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(object.getCenter());
		assertTrue(collisionObjects.contains(object));
		assertEquals(collisionObjects.size(), 1);
	}

	@Test
	public void insertObjectOnPosition4_4_shouldBeAbleToRetrieveIt()
	{
		instertObjectOnPoistion_shouldBeAbleToRetreiveIt(calcPosition(4, 4));
	}
	
	private Point calcPosition(int x, int y)
	{
		return new Point(x*containerWidth, y*containerHeight);
	}
	
	@Test
	public void insertObjectThenRemove_shouldRsetrieveNothing()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(1, 1));
		collisionMap.insert(object);
		collisionMap.remove(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(1, 1));
		assertTrue(collisionObjects.isEmpty());
	}
	
	@Test
	public void insertObject1_1_thenMoveRight_shouldBeOn2_1()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(1, 1));
		collisionMap.insert(object);
		collisionMap.moveRight(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(2, 1));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject2_1_thenMoveRight_shouldBeOn3_1()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 1));
		collisionMap.insert(object);
		collisionMap.moveRight(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(3, 1));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject2_1_thenMoveRight2_1_shouldBeEmpty()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 1));
		collisionMap.insert(object);
		collisionMap.moveRight(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(2, 1));
		assertTrue(collisionObjects.isEmpty());
	}
	
	@Test
	public void insertObject1_1_thenMoveRight1_1_shouldBeEmpty()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(1, 1));
		collisionMap.insert(object);
		collisionMap.moveRight(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(1, 1));
		assertTrue(collisionObjects.isEmpty());
	}
	
	@Test
	public void insertObject2_2_thenMoveDown_shouldBeOn2_3()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 2));
		collisionMap.insert(object);
		collisionMap.moveDown(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(2, 3));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject2_3_thenMoveUp_shouldBeOn2_2()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 3));
		collisionMap.insert(object);
		collisionMap.moveUp(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(2, 2));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject2_2_thenMoveDownTwice_shouldBeOn2_4()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 2));
		collisionMap.insert(object);
		collisionMap.moveDown(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 3));
		collisionMap.moveDown(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(2, 4));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject2_2_thenMoveLeft_shouldBeOn1_2()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 2));
		collisionMap.insert(object);
		collisionMap.moveLeft(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(1, 2));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject3_2_thenMoveLeftThreeTimes_shouldBeOn0_2()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(3, 2));
		collisionMap.insert(object);
		collisionMap.moveLeft(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 2));
		collisionMap.moveLeft(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(1, 2));
		collisionMap.moveLeft(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(0, 2));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test
	public void insertObject4_4_thenMoveUpDownLeftTwiceRight_shouldBeOn3_4()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(4, 4));
		collisionMap.insert(object);
		collisionMap.moveUp(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(4, 3));
		collisionMap.moveDown(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(4, 4));
		collisionMap.moveLeft(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(3, 4));
		collisionMap.moveLeft(object);
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(2, 4));
		collisionMap.moveRight(object);
		Collection<StackableCollisionObject> collisionObjects = collisionMap.getCollisionObjectsFromSingleContainer(calcPosition(3, 4));
		assertTrue(collisionObjects.contains(object));
	}
	
	@Test(expected = OutOfBoundsException.class)
	public void insertObjectOnBorder5_5_thenMoveDown_shouldThrowOutOfBoundsException()
	{
		Mockito
		.when(object.getCenter())
		.thenReturn(calcPosition(5, 5)); 
		collisionMap.insert(object);
		collisionMap.moveDown(object);
	}

	@Test
	public void detectCollidingObject2_2And2_3Collision()
	{
		StackableCollisionObject firstCollisionObject = mockObject(calcPosition(2, 2));
		StackableCollisionObject secondCollisionObject = mockObject(calcPosition(2, 3));
		Mockito
		.when(secondCollisionObject.isColliding(firstCollisionObject))
		.thenReturn(true);
		Mockito
		.when(firstCollisionObject.isColliding(secondCollisionObject))
		.thenReturn(true);
		collisionMap.insert(firstCollisionObject);
		collisionMap.insert(secondCollisionObject);
		
		Collection<StackableCollisionObject> collidingObjects = collisionMap.getCollidingObjects(firstCollisionObject);
		assertTrue(!collidingObjects.contains(firstCollisionObject));
		assertTrue(collidingObjects.contains(secondCollisionObject));
	}

	@Test
	public void detectCollidingObject2_2And3_3Collision()
	{
		StackableCollisionObject firstCollisionObject = mockObject(calcPosition(2, 2));
		StackableCollisionObject secondCollisionObject = mockObject(calcPosition(3, 3));
		collisionMap.insert(firstCollisionObject);
		collisionMap.insert(secondCollisionObject);
		Mockito
		.when(firstCollisionObject.isColliding(secondCollisionObject))
		.thenReturn(true);
		Mockito
		.when(secondCollisionObject.isColliding(firstCollisionObject))
		.thenReturn(true);
		Collection<StackableCollisionObject> collidingObjects = collisionMap.getCollidingObjects(firstCollisionObject);
		assertTrue(!collidingObjects.contains(firstCollisionObject));
		assertTrue(collidingObjects.contains(secondCollisionObject));
	}
	
	@Test
	public void detectCollidingObject0_0And1_1Collision()
	{
		StackableCollisionObject firstCollisionObject = mockObject(calcPosition(0, 0));
		StackableCollisionObject secondCollisionObject = mockObject(calcPosition(1, 1));
		collisionMap.insert(firstCollisionObject);
		collisionMap.insert(secondCollisionObject);
		Mockito
		.when(firstCollisionObject.isColliding(secondCollisionObject))
		.thenReturn(true);
		Mockito
		.when(secondCollisionObject.isColliding(firstCollisionObject))
		.thenReturn(true);
		Collection<StackableCollisionObject> collidingObjects = collisionMap.getCollidingObjects(firstCollisionObject);
		assertTrue(!collidingObjects.contains(firstCollisionObject));
		assertTrue(collidingObjects.contains(secondCollisionObject));
	}
	
	@Test
	public void detectCollidingObject5_5And5_4Collision()
	{
		StackableCollisionObject firstCollisionObject = mockObject(calcPosition(5, 5));
		StackableCollisionObject secondCollisionObject = mockObject(calcPosition(5, 4));
		collisionMap.insert(firstCollisionObject);
		collisionMap.insert(secondCollisionObject);
		Mockito
		.when(firstCollisionObject.isColliding(secondCollisionObject))
		.thenReturn(true);
		Mockito
		.when(secondCollisionObject.isColliding(firstCollisionObject))
		.thenReturn(true);
		Collection<StackableCollisionObject> collidingObjects = collisionMap.getCollidingObjects(firstCollisionObject);
		assertTrue(!collidingObjects.contains(firstCollisionObject));
		assertTrue(collidingObjects.contains(secondCollisionObject));
	}

	private StackableCollisionObject mockObject(Point position)
	{
		StackableCollisionObject object = Mockito.mock(StackableCollisionObject.class);
		Mockito
		.when(object.getCenter())
		.thenReturn(position);

		return object;
	}

	
}
