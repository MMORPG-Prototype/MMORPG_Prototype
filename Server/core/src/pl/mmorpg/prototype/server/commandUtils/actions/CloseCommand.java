package pl.mmorpg.prototype.server.commandUtils.actions;

import com.badlogic.gdx.Gdx;

import pl.mmorpg.prototype.server.ServerSettings;

public class CloseCommand implements CommandAction
{

	@Override
	public void run(String args)
	{
		if(ServerSettings.isHeadless)
			System.exit(0);
		else
			Gdx.app.exit();
	}

	@Override
	public String getDescription()
	{
		return "Close application";
	}

	@Override
	public String getName()
	{
		return "exit";
	}
	
}
