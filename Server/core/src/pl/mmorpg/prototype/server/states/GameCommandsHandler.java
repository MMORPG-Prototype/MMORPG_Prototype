package pl.mmorpg.prototype.server.states;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

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

	public Object execute(String code) throws ScriptException
	{
		if (codeRunsExecuteMethod(code))
			throw new ScriptException("Cannot run execute method directly!");
		return javaScriptEngine.eval(OBJECT_NAME + '.' + code);
	}

	private boolean codeRunsExecuteMethod(String code)
	{
		code = code.trim();
		String executeMethodName = "execute";
		return code.startsWith(executeMethodName) && code.length() > executeMethodName.length()
				&& code.charAt(executeMethodName.length()) == '(';
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

	public String help()
	{
		 return Arrays.stream(getClass().getDeclaredMethods())
				.filter(this::isPublic)
				.map(Method::getName)
				.filter(method -> !method.equals("execute") && !method.equals("help"))
				.collect(Collectors.joining("\n"));
	}

	private boolean isPublic(Method method)
	{
		final int access = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE;
		return (method.getModifiers() & access) == Modifier.PUBLIC;
	}
}
