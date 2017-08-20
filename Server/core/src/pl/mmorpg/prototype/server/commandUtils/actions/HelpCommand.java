package pl.mmorpg.prototype.server.commandUtils.actions;

import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;

public class HelpCommand implements CommandAction
{
	private Map<String, CommandAction> commands;

	public HelpCommand(Map<String, CommandAction> commands)
	{
		this.commands = commands;
	}

	@Override
	public void run(String args)
	{
		Collection<String> names = new TreeSet<String>(commands.keySet());
		for(String commandName : names)
		{
			String formatedName = formatName(commandName);
			CommandAction command = commands.get(commandName);
			System.out.println("\t" + formatedName + command.getDescription());
		}
	}

	private String formatName(String commandName)
	{
		StringBuilder strBuilder = new StringBuilder(commandName);
		while(strBuilder.length() < 25)
			strBuilder.append(' ');
		String formatedName = strBuilder.toString();
		return formatedName;
	}


	@Override
	public String getDescription()
	{
		return "A help command";
	}

	@Override
	public String getName()
	{
		return "help";
	}

	

}
