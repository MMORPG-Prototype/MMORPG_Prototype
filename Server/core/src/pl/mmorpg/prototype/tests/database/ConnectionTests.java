package pl.mmorpg.prototype.tests.database;

import org.junit.Test;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.EntityExampleTest;

public class ConnectionTests
{
	@Test
	public void testConnectionByAddingAndRemovingRecord()
	{
		EntityExampleTest testRecord = new EntityExampleTest();
		testRecord.setData("asdasd");
		HibernateUtil.makeOperation((session) -> session.save(testRecord));
		HibernateUtil.makeOperation((session) -> session.remove(testRecord));
	}
}
