package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.Session;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class UserCharactersTableSeeder extends TableSeeder
{

	@Override
	public Collection<Object> getRecords()
	{
		Collection<Object> records = new LinkedList<>();
		records.add(createUserCharacter("Pankiev", "PankievChar"));
		records.add(createUserCharacter("Smyk", "SmykChar"));
		return records;
	}

	private Object createUserCharacter(String username, String characterName)
	{
		Session session = HibernateUtil.openSession();
		session.beginTransaction();
		

		User user = session
				.createQuery("from " + User.class.getName() + " as U where U.username = :username", User.class)
				.setParameter("username", username)
				.getSingleResult();
		UserCharacter character = new UserCharacter();
		character.setUser(user);
		character.setNickname(characterName);
		session.getTransaction().commit();
		session.close();
		return character;
	}

}
