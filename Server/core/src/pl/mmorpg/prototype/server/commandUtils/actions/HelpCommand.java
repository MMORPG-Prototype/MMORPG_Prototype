package pl.mmorpg.prototype.server.commandUtils.actions;

import java.util.Map;

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
		for(CommandAction command : commands.values())
			System.out.println(String.format("%20s\t\t%s ", command.getName(), command.getDescription()));
	}


	@Override
	public String getDescription()
	{
		return "A help command";
	}

	@Override
	public String getName()
	{
		return "-help";
	}

	

}
