package pl.mmorpg.prototype.server.input;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.badlogic.gdx.Input.Keys;

import pl.mmorpg.prototype.server.exceptions.GameException;


public class KeyHandlerFactory
{
	private int keyHandlerBaseClassNameLength;
	private String desiredKeyName;
	private InputProcessorAdapter inputHandler;

	public KeyHandlerFactory(InputProcessorAdapter inputHandler)
	{
		this.inputHandler = inputHandler;
		keyHandlerBaseClassNameLength = KeyHandler.class.getSimpleName().length();
	}

	public KeyHandler produce(int key)
	{
		desiredKeyName = Keys.toString(key);
		Class<?>[] keyHandlers = inputHandler.getClass().getDeclaredClasses();
		for (Class<?> keyHandler : keyHandlers)
			if (isPublic(keyHandler) && isDesiredKeyHandler(keyHandler))
				return tryCreatingInstance(keyHandler, inputHandler);

		return KeyHandler.EMPTY;
	}

	private boolean isDesiredKeyHandler(Class<?> keyHandler)
	{
		String actualKeyName = getFirstWordFromName(keyHandler);
		return desiredKeyName.equals(actualKeyName);
	}

	private String getFirstWordFromName(Class<?> keyHandler)
	{
		String simpleName = keyHandler.getSimpleName();
		String firstWord = simpleName.substring(0, simpleName.length() - keyHandlerBaseClassNameLength);
		return firstWord;
	}

	private KeyHandler tryCreatingInstance(Class<?> keyHandler, InputProcessorAdapter inputHandler)
	{
		try
		{
			Constructor<?> keyHandlerConstructor = keyHandler
					.getDeclaredConstructor(new Class[] { inputHandler.getClass() });
			KeyHandler actualKeyHandler = (KeyHandler) keyHandlerConstructor.newInstance(new Object[] { inputHandler });
			return actualKeyHandler;

		} catch (Exception e)
		{
			throw new CannotCreateKeyHandlerException(e.getMessage());
		}
	}

	public KeyHandlers produceAll()
	{
		KeyHandlers keyHandlers = new KeyHandlers();
		Class<?>[] keyHandlersTypes = inputHandler.getClass().getDeclaredClasses();
		for (Class<?> keyHandlerType : keyHandlersTypes)
			keyHandlers = addKeyHandlerIfPublic(keyHandlers, keyHandlerType);

		return keyHandlers;
	}

	private KeyHandlers addKeyHandlerIfPublic(KeyHandlers keyHandlers, Class<?> keyHandlerType)
	{
		if (isPublic(keyHandlerType))
			addKeyHandler(keyHandlers, keyHandlerType);
		return keyHandlers;
	}

	private boolean isPublic(Class<?> keyHandlerType)
	{
		return keyHandlerType.getModifiers() == Modifier.PUBLIC;
	}

	private void addKeyHandler(KeyHandlers keyHandlers, Class<?> keyHandlerType)
	{
		KeyHandler keyHandler = tryCreatingInstance(keyHandlerType, inputHandler);
		String key = getFirstWordFromName(keyHandlerType);
		keyHandlers.put(key, keyHandler);
	}

	private class CannotCreateKeyHandlerException extends GameException
	{
		protected CannotCreateKeyHandlerException(String message)
		{
			super(message);
		}
	}
}
