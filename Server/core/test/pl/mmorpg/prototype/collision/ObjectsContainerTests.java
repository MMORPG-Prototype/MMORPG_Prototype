package pl.mmorpg.prototype.collision;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.mmorpg.prototype.server.collision.interfaces.CollisionObjectsContainer;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.collision.stackablemap.ObjectsContainer;
import pl.mmorpg.prototype.server.exceptions.DuplicateObjectException;
import pl.mmorpg.prototype.server.exceptions.ThereIsNoSuchObjectToRemoveException;

@RunWith(MockitoJUnitRunner.class)
public class ObjectsContainerTests
{
	@Mock
	private StackableCollisionObject object;
	
	private CollisionObjectsContainer<StackableCollisionObject> container;
	
	@Before
	public void createContainer()
	{
		container = new ObjectsContainer<>();
	}
	
	@Test
	public void insertObject_shouldBeThere()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.insertObject(object);
		assertTrue(container.getObjects().contains(object));
		assertTrue(container.getObjects().size() == 1);
	}
	
	@Test
	public void insertAndRemoveObject_shouldBeEmpty()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.insertObject(object);
		container.removeOrThrow((object));
		assertTrue(container.getObjects().isEmpty());
	}
	
	@Test(expected = DuplicateObjectException.class)
	public void insertSameObjectTwice_shouldThrowDuplicateObjectIdException()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.insertObject(object);
		container.insertObject(object);
	}
	
	@Test(expected = ThereIsNoSuchObjectToRemoveException.class)
	public void removeNotInsertedObject_shouldThrowThereIsNoSuchObjectToRemoveException()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.removeOrThrow((object));
	}
	
	@Test
	public void insertMultipleObjects_checkIfTheLastIsOnTop()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(3);
		StackableCollisionObject secObj = Mockito.mock(StackableCollisionObject.class);
		StackableCollisionObject thirdObj = Mockito.mock(StackableCollisionObject.class);
		StackableCollisionObject fourthObj = Mockito.mock(StackableCollisionObject.class);
		
		container.insertObject(secObj);
		container.insertObject(thirdObj);
		container.insertObject(fourthObj);
		container.insertObject(object);
		assertSame(object, container.top());
	}
	
	@Test
	public void insertMultipleObjects_checkOrderByRemoving()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(0);
		StackableCollisionObject secObj = Mockito.mock(StackableCollisionObject.class);
		Mockito
		.when(secObj.getCollisionContainerId())
		.thenReturn(1);
		StackableCollisionObject thirdObj = Mockito.mock(StackableCollisionObject.class);
		Mockito
		.when(thirdObj.getCollisionContainerId())
		.thenReturn(2);
		StackableCollisionObject fourthObj = Mockito.mock(StackableCollisionObject.class);
		Mockito
		.when(fourthObj.getCollisionContainerId())
		.thenReturn(3);

		container.insertObject(object);
		container.insertObject(secObj);
		container.insertObject(thirdObj);
		container.insertObject(fourthObj);
		assertSame(fourthObj, container.removeFromTop());
		assertSame(thirdObj, container.removeFromTop());
		assertSame(secObj, container.removeFromTop());
		assertSame(object, container.removeFromTop());
	}
	
}
