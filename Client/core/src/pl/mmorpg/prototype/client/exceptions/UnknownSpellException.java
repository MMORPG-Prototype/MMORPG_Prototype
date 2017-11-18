package pl.mmorpg.prototype.client.exceptions;

public class UnknownSpellException extends GameException
{
	public UnknownSpellException(String identifier)
	{
		super(identifier);
	}

	public UnknownSpellException(Class<?> type)
	{
		super(type.getName());
	}
}
