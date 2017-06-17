package pl.mmorpg.prototype.server.commandUtils.actions;

import java.util.Map;

public class HelpAction implements CommandAction
{
	private Map<String, CommandAction> commands;

	public HelpAction(Map<String, CommandAction> commands)
	{
		this.commands = commands;
	}

	@Override
	public void run(String args)
	{
		for(CommandAction command : commands.values())
			System.out.println(command.getName() + "\t " + command.getDescription());
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
