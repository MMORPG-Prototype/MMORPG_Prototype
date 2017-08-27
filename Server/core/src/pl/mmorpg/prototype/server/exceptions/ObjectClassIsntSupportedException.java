package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.objects.GameObject;

public class ObjectClassIsntSupportedException extends GameException
{
	public ObjectClassIsntSupportedException(Class<? extends GameObject> type)
	{
		super(type.getSimpleName());
	}
}
