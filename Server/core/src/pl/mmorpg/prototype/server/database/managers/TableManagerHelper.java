package pl.mmorpg.prototype.server.database.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public class TableManagerHelper
{
	public static <T> List<T> getListOfDataElements(String entityName, String whereCondition, Class<T> type)
	{
		Session session = HibernateUtil.openSession();
		Query<T> countUsersQuery = session
				.createQuery("from " + entityName + " as " + entityName + " where " + whereCondition, type);
		List<T> users = countUsersQuery.getResultList();
		session.close();
		return users;
	}

}
