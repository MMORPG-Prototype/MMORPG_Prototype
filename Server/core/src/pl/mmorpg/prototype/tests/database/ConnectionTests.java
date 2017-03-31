package pl.mmorpg.prototype.tests.database;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public class ConnectionTests
{
	@Test
	public void testConnectionByAddingAndRemovingRecord()
	{
		Session session = HibernateUtil.openSession();
		Transaction tx = session.beginTransaction();
		EntityExampleTest testRecord = new EntityExampleTest();
		testRecord.setData("asdasd");
		session.save(testRecord);
		tx.commit();
		tx = session.beginTransaction();
		session.remove(testRecord);
		tx.commit();
		session.close();
	}
}
