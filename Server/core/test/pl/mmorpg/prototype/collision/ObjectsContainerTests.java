package pl.mmorpg.prototype.collision;

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
import pl.mmorpg.prototype.server.exceptions.DuplicateObjectIdException;
import pl.mmorpg.prototype.server.exceptions.ThereIsNoSuchObjectToRemoveException;

@RunWith(MockitoJUnitRunner.class)
public class ObjectsContainerTests
{
	@Mock
	private StackableCollisionObject object;
	
	private CollisionObjectsContainer container;
	
	@Before
	public void createContainer()
	{
		container = new ObjectsContainer();
	}
	
	@Test
	public void insertObject_shouldBeThere()
	{
		Mockito
		.when(object.getUniqueId())
		.thenReturn(1);
		container.insertObject(object);
		assertTrue(container.getObjects().contains(object));
		assertTrue(container.getObjects().size() == 1);
	}
	
	@Test
	public void insertAndRemoveObject_shouldBeEmpty()
	{
		Mockito
		.when(object.getUniqueId())
		.thenReturn(1);
		container.insertObject(object);
		container.removeOrThrow((object));
		assertTrue(container.getObjects().isEmpty());
	}
	
	@Test(expected = DuplicateObjectIdException.class)
	public void insertSameObjectTwice_shouldThrowDuplicateObjectIdException()
	{
		Mockito
		.when(object.getUniqueId())
		.thenReturn(1);
		container.insertObject(object);
		container.insertObject(object);
	}
	
	@Test(expected = ThereIsNoSuchObjectToRemoveException.class)
	public void removeNotInsertedObject_shouldThrowThereIsNoSuchObjectToRemoveException()
	{
		Mockito
		.when(object.getUniqueId())
		.thenReturn(1);
		container.removeOrThrow((object));
	}
	
}
