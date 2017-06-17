package pl.mmorpg.prototype.server.states;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;

public class GameCommandsHandler
{
	private final static String OBJECT_NAME = "Game";
	private final ScriptEngine javaScriptEngine;
	private PlayState game;
	
	public GameCommandsHandler(PlayState game)
	{
		this.game = game;
		ScriptEngineManager manager = new ScriptEngineManager();
		javaScriptEngine = manager.getEngineByName("JavaScript");
		javaScriptEngine.put(OBJECT_NAME, this);
	}
	
	public void execute(String code) throws ScriptException
	{
		javaScriptEngine.eval(code);
	}
	
	public void addMonster(String identifier, int x, int y)
	{
		game.addMonster(identifier, x, y);
	}
	
	public void addGreenDragon(int x, int y)
	{
		game.addMonster(ObjectsIdentifiers.GREEN_DRAGON, x, y);
	}

	public void addRedDragon(int x, int y)
	{
		game.addMonster(ObjectsIdentifiers.RED_DRAGON, x, y);
	}
}
