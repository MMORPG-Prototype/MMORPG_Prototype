package pl.mmorpg.prototype.server.commandUtils.actions;

import pl.mmorpg.prototype.server.database.Settings;

public class SetInternalExternalDatabaseConfigPath implements CommandAction
{
	private static final String externalCmd = "-external";
	private static final String internalCmd = "-internal";

	@Override
	public void run(String args)
	{
		if(args == null)
		{
			System.out.println("Not enough arguments");
			return;
		}
		String cmd = args.trim();
		
		if(cmd.equals(externalCmd))
			Settings.HIBERNATE_CONFIG_INTERNAL_PATH = false;
		else if(cmd.equals(internalCmd))
			Settings.HIBERNATE_CONFIG_INTERNAL_PATH = true;
		else
		{
			System.out.println("Unrecognized argument " + cmd);
			return;		
		}
		System.out.println("Location set");
	}

	@Override
	public String getDescription()
	{
		return "Set searching for hibernate configuration file to external(" + externalCmd + ") or internal("
				+ internalCmd + ") location";
	}

	@Override
	public String getName()
	{
		return "-db-config-location";
	}

}
