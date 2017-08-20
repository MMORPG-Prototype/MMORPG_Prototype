package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.server.database.HibernateUtil;

public class DatabaseRecreateCommand implements CommandAction
{
	@Override
	public void run(String args)
	{
		HibernateUtil.recreateDatabase();
	}

	@Override
	public String getDescription()
	{
		return "Drop all tables and then create empty ones";
	}

	@Override
	public String getName()
	{
		return "database-recreate";
	}

}
