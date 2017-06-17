package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserRole;

public class UsersTableSeeder extends TableSeeder
{

	@Override
	public Collection<Object> getRecords()
	{
		Collection<Object> records = new LinkedList<>();
		records.add(createUser("Pankiev", "password123", UserRole.USER));
		records.add(createUser("Smyk", "bro", UserRole.USER));
		records.add(createUser("admin", "admin", UserRole.ADMIN));
		return records;
	}

	private User createUser(String username, String password, UserRole role)
	{
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		return user;
	}

}
