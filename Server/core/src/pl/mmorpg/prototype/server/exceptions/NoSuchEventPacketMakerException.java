package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.quests.events.Event;

public class NoSuchEventPacketMakerException extends GameException
{
	public NoSuchEventPacketMakerException(Class<? extends Event> notFoundType)
	{
		super("Could not find packet maker for type: " + notFoundType.getName());
	}
}
