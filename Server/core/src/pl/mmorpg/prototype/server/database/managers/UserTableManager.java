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
		Query<User> getUserQuery = getGetUserByUsernameQuery(username, session);
		User user = getUserQuery.getSingleResult();
		session.close();
		return user;
	}


	private static Query<User> getGetUserByUsernameQuery(String username, Session session)
	{
		Query<User> getUserQuery = session
				.createQuery("from User as user where user.username = :username", User.class)
				.setParameter("username", username);
		return getUserQuery;
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
	
	public static void updateUser(User user)
	{
		HibernateUtil.makeOperation( session -> 
		{
			Query<User> getUserByUserIdQuery = getGetUserByUserIdQuery(session, user.getId());
			User dbUser = getUserByUserIdQuery.getSingleResult();
			dbUser.setUsername(user.getUsername());
			dbUser.setPassword(user.getPassword());
			dbUser.setRole(user.getRole());
			session.save(dbUser);
		});
	}
	
	public static void addUser(User user)
	{
		HibernateUtil.makeOperation((session) -> session.save(user));
	}

	public static User getUser(int userId)
	{
		Session session = HibernateUtil.openSession();
		Query<User> getUserQuery = getGetUserByUserIdQuery(session, userId);
		User user = getUserQuery.getSingleResult();
		session.close();
		return user;
	}


	private static Query<User> getGetUserByUserIdQuery(Session session, int userId)
	{
		Query<User> getUserQuery = session
				.createQuery("from User as user where user.id = :userId", User.class)
				.setParameter("userId", userId);
		return getUserQuery;
	}
}
