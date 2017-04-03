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
		List<UserCharacter> users = TableManagerHelper.getListOfDataElements("UserCharacter",
				"UserCharacter.user.id = " + user.getId(), UserCharacter.class);
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
		Query<UserCharacter> getUserCharacterQuery = session
				.createQuery("from " + "UserCharacter" + " as character where character.id = :id", UserCharacter.class)
				.setParameter("id", id);
		UserCharacter charcter = getUserCharacterQuery.getSingleResult();
		session.close();
		return charcter;
	}
}
