package pl.mmorpg.prototype.client.exceptions;

public class UnknownItemIdentifierException extends GameException
{

	public UnknownItemIdentifierException(String identifier)
	{
		super(identifier);
	}

}
