package pl.mmorpg.prototype.server.commandUtils;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.server.commandUtils.actions.ChangeDatabaseConfigFilePathCommand;
import pl.mmorpg.prototype.server.commandUtils.actions.CloseCommand;
import pl.mmorpg.prototype.server.commandUtils.actions.CommandAction;
import pl.mmorpg.prototype.server.commandUtils.actions.HelpCommand;
import pl.mmorpg.prototype.server.commandUtils.actions.SeedDatabaseCommand;
import pl.mmorpg.prototype.server.commandUtils.actions.SetInternalExternalDatabaseConfigPath;
import pl.mmorpg.prototype.server.commandUtils.actions.ShowRegisteredUsersCommand;
import pl.mmorpg.prototype.server.commandUtils.actions.UserChangeRoleCommand;

public class CommandHandler
{
	private Map<String, CommandAction> commands = new HashMap<>();
	
	public CommandHandler()
	{
		initializeCommands();
	}
	
	private void initializeCommands()
	{
		addCommand(new HelpCommand(commands));
		addCommand(new ChangeDatabaseConfigFilePathCommand());
		addCommand(new SetInternalExternalDatabaseConfigPath());
		addCommand(new UserChangeRoleCommand());
		addCommand(new CloseCommand());
		addCommand(new ShowRegisteredUsersCommand());
		addCommand(new SeedDatabaseCommand());
	}
	
	private void addCommand(CommandAction command)
	{
		commands.put(command.getName(), command);
	}

	public void handle(String command)
	{
		command = command.trim();
		int indexOfSpace = command.indexOf(' ');
		CommandAction commandAction;
		String commandArgs = null;
		if(indexOfSpace != -1)
		{
			commandArgs = command.substring(indexOfSpace + 1);
			String commandName = command.substring(0, indexOfSpace);
			commandAction = commands.get(commandName);
		}
		else
			commandAction = commands.get(command);
		
		if(commandAction != null)
			commandAction.run(commandArgs);
		else
			System.out.println("Unrecognized command: " + command + ". You can type \"help\" for help");
	}

}
