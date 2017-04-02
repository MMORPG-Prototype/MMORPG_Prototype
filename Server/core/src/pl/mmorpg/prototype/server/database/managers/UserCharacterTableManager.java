package pl.mmorpg.prototype.server.database.managers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class UserCharacterTableManager
{
	public static List<UserCharacter> getUserCharacters(User user)
	{
		Session session = HibernateUtil.openSession();
		Query<UserCharacter> countUsersQuery = session
				.createQuery("from " + "UserCharacter"
						+ " as userChar where userChar.user.id = :userId",
						UserCharacter.class)
				.setParameter("userId", user.getId());
		List<UserCharacter> users = countUsersQuery.getResultList();
		session.close();
		return users;
	}

	public static List<UserCharacter> getUserCharacters(String username)
	{
		User user = UserTableManager.getUser(username);
		return getUserCharacters(user);
	}

	public static UserCharacter getUserCharacter(int id)
	{
		Session session = HibernateUtil.openSession();
		Query<UserCharacter> countUsersQuery = session
				.createQuery("from " + "UserCharacter" + " as userChar where userChar.id = :id", UserCharacter.class)
				.setParameter("id", id);
		UserCharacter user = countUsersQuery.getSingleResult();
		session.close();
		return user;
	}
}
