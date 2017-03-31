package pl.mmorpg.prototype.server.objects;

import pl.mmorpg.prototype.server.exceptions.UnknownTypeException;

public class GameObjectsFactory
{
	public static GameObject create(String type)
	{
		if (type.compareToIgnoreCase("NullObject") == 0)
			return GameObject.NULL_OBJECT;

		throw new UnknownTypeException(type);
	}
}
