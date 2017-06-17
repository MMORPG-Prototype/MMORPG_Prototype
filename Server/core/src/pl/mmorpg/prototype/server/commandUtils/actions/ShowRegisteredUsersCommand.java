package pl.mmorpg.prototype.server.commandUtils.actions;

import java.util.Collection;

import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.managers.UserTableManager;

public class ShowRegisteredUsersCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		Collection<User> allUsers = UserTableManager.getAllUsers();
		for(User user : allUsers)
			System.out.println(user);
	}

	@Override
	public String getDescription()
	{
		return "Show all registered users";
	}

	@Override
	public String getName()
	{
		return "users-show";
	}

}
