package pl.mmorpg.prototype.server.states;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;

public class GameCommandsHandler
{
	private final static String OBJECT_NAME = "Game";
	private final ScriptEngine javaScriptEngine;
	private PlayState game;
	private UserInfo userInfo;

	public GameCommandsHandler(PlayState game, UserInfo userInfo)
	{
		this.game = game;
		this.userInfo = userInfo;
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
		game.addGameObject(identifier, x, y);
	}

	public void addGreenDragon(int x, int y)
	{
		game.addGameObject(ObjectsIdentifiers.GREEN_DRAGON, x, y);
	}

	public void addRedDragon(int x, int y)
	{
		game.addGameObject(ObjectsIdentifiers.RED_DRAGON, x, y);
	}
	
	public void addQuestBoard(int x, int y)
	{
		game.addGameObject(ObjectsIdentifiers.QUEST_BOARD, x, y);
	}
	
	public void additem(String identifier, int inventoryX, int inventoryY, int inventoryPage) throws ScriptException
	{
		addItem(identifier, 1, inventoryX, inventoryY, inventoryPage);
	}

	public void addItem(String identifier, int amount, int inventoryX, int inventoryY, int inventoryPage)
			throws ScriptException
	{
		InventoryPosition position = new InventoryPosition(inventoryPage, inventoryX, inventoryY);
		ItemIdentifiers itemIdentifier = tryConvertToItemIdentifier(identifier);
		game.addItem(itemIdentifier, amount, userInfo.userCharacter, position);
	}

	private ItemIdentifiers tryConvertToItemIdentifier(String identifier) throws ScriptException
	{
		try
		{
			return ItemIdentifiers.valueOf(identifier);		
		}
		catch(IllegalArgumentException e)
		{
			throw new ScriptException(new UnknownItemTypeException(identifier));
		}
	}

	public String help()
	{
		 return Arrays.stream(getClass().getDeclaredMethods())
				.filter(this::isPublic)
				.filter(method -> !method.getName().equals("execute") && !method.getName().equals("help"))
				.map(this::createMethodInfo)
				.collect(Collectors.joining("\n"));
	}

	private String createMethodInfo(Method method)
	{
		StringBuilder methodInfo = new StringBuilder(method.getName());
		methodInfo.append('(');
		String parameters = Arrays.stream(method.getParameterTypes())
			.map(Class::getSimpleName)
			.collect(Collectors.joining(", "));
		methodInfo.append(parameters);
		methodInfo.append(");");
		return methodInfo.toString();
	}

	private boolean isPublic(Method method)
	{
		final int access = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE;
		return (method.getModifiers() & access) == Modifier.PUBLIC;
	}
}
