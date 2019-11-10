package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.server.database.seeders.DatabaseSeeder;

public class DatabaseSeedCommand implements CommandAction
{
	private final DatabaseSeeder databaseSeeder = new DatabaseSeeder();

	@Override
	public void run(String args)
	{
		databaseSeeder.seed();
	}

	@Override
	public String getDescription()
	{
		return "Seed database with implemented seeders (to use particulary for empty database)";
	}

	@Override
	public String getName()
	{
		return "database-seed";
	}
}
