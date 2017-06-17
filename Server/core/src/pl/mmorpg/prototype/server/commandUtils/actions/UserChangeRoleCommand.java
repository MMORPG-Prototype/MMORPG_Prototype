package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserRole;
import pl.mmorpg.prototype.server.database.managers.UserTableManager;

public class UserChangeRoleCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		String[] split = args.split(" ");
		String username = split[0];
		UserRole userRole = UserRole.valueOf(split[1]);	
		User user = UserTableManager.getUser(username);
		user.setRole(userRole);
		UserTableManager.updateUser(user);
	}

	@Override
	public String getDescription()
	{
		return "Change user role to specified value, usage: user-role <username> <ADMIN or USER>";
	}

	@Override
	public String getName()
	{
		return "user-role";
	}
	
}
