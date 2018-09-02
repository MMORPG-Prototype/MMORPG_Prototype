package pl.mmorpg.prototype.collision;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mmorpg.prototype.server.collision.interfaces.CollisionObjectsContainer;
import pl.mmorpg.prototype.server.collision.interfaces.StackableCollisionObject;
import pl.mmorpg.prototype.server.collision.stackablemap.ObjectsContainer;
import pl.mmorpg.prototype.server.exceptions.DuplicateObjectException;
import pl.mmorpg.prototype.server.exceptions.ThereIsNoSuchObjectToRemoveException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ObjectsContainerTests
{
	@Mock
	private StackableCollisionObject object;
	
	private CollisionObjectsContainer<StackableCollisionObject> container;
	
	@BeforeEach
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
		assertEquals(1, container.getObjects().size());
	}
	
	@Test
	public void insertAndRemoveObject_shouldBeEmpty()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.insertObject(object);
		container.removeOrThrow(object);
		assertTrue(container.getObjects().isEmpty());
	}
	
	@Test
	public void insertSameObjectTwice_shouldThrowDuplicateObjectIdException()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		container.insertObject(object);
		assertThrows(DuplicateObjectException.class, () -> container.insertObject(object));
	}
	
	@Test
	public void removeNotInsertedObject_shouldThrowThereIsNoSuchObjectToRemoveException()
	{
		Mockito
		.when(object.getCollisionContainerId())
		.thenReturn(1);
		assertThrows(ThereIsNoSuchObjectToRemoveException.class, () -> container.removeOrThrow(object));
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
