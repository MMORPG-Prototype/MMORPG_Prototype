package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.data.entities.repositories.UserRepository;

public class ShowRegisteredUsersCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		UserRepository characterRepo = SpringContext.getBean(UserRepository.class);
		characterRepo.findAll().forEach(user -> System.out.println(user));
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
