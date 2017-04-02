package pl.mmorpg.prototype.server.database.managers;

import org.hibernate.Session;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.User;

public class UserTableManager
{

	public static User getUser(String username)
	{
		Session session = HibernateUtil.openSession();
		Query<User> getUserQuery = session
				.createQuery("from " + User.class.getName() + " where username = :username", User.class)
				.setParameter("username", username);
		User user = getUserQuery.getSingleResult();
		session.close();
		return user;
	}

	public static boolean hasUser(String username)
	{
		Session session = HibernateUtil.openSession();
		Query<Long> countUsersQuery = session
				.createQuery("select count(user) from " + User.class.getName() + " user where username = :username",
						Long.class)
				.setParameter("username", username);
		Long userCount = countUsersQuery.getSingleResult();
		session.close();
		return userCount > 0;
	}

	public static void addUser(User user)
	{
		HibernateUtil.makeOperation((session) -> session.save(user));
	}
}
