package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.server.database.Settings;

public class ChangeDatabaseConfigFilePathCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		if(args == null)
		{
			System.out.println("Not enough arguments");
			return;
		}
		String configFilePath = args.trim();
		Settings.HIBERNATE_CONFIG_FILE_PATH = configFilePath;
		System.out.println("Config path changed to: " + configFilePath);
	}

	@Override
	public String getDescription()
	{
		return "Change database configuration filepath";
	}

	@Override
	public String getName()
	{
		return "db-config";
	}

}
