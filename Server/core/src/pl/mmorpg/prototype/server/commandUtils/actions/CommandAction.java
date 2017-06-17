package pl.mmorpg.prototype.server.commandUtils.actions;

public interface CommandAction
{
	void run(String args);
	
	String getDescription();
	
	String getName();
}
