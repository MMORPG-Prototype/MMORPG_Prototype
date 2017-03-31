package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.server.database.entities.User;

public class UsersTableSeeder extends TableSeeder
{

	@Override
	public Collection<Object> getRecords()
	{
		Collection<Object> records = new LinkedList<>();
		records.add(createUser("Pankiev", "password123"));
		records.add(createUser("Smyk", "bro"));
		return records;
	}

	private User createUser(String username, String password)
	{
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

}
