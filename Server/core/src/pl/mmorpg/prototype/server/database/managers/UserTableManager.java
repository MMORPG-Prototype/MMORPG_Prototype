package pl.mmorpg.prototype.server.database.managers;

import javax.persistence.NoResultException;

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
				.createQuery("from User as user where user.username = :username", User.class)
				.setParameter("username", username);
		User user = getUserQuery.getSingleResult();
		session.close();
		return user;
	}

	public static boolean hasUser(String username)
	{
		try
		{
			getUser(username);
			return true;
		} catch (NoResultException e)
		{
			return false;
		}
	}

	public static void addUser(User user)
	{
		HibernateUtil.makeOperation((session) -> session.save(user));
	}
}
