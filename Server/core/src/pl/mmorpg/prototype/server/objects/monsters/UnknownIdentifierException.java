package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.exceptions.GameException;

public class UnknownIdentifierException extends GameException
{
	public UnknownIdentifierException(String identifier)
	{
		super(identifier);
	}
}
