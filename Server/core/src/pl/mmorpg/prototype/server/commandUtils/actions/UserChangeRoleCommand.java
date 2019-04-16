package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.data.entities.User;
import pl.mmorpg.prototype.data.entities.UserRole;
import pl.mmorpg.prototype.data.entities.repositories.UserRepository;

public class UserChangeRoleCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		UserRepository userRepo = SpringContext.getBean(UserRepository.class);
		String[] split = args.split(" ");
		String username = split[0];
		UserRole userRole = UserRole.valueOf(split[1]);	
		User user = userRepo.findByUsername(username);
		user.setRole(userRole);
		userRepo.save(user);
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
