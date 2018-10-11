package pl.mmorpg.prototype.client.exceptions;

public class ObjectIdentifierNotFoundException extends GameException
{
	public ObjectIdentifierNotFoundException(String identifier)
	{
		super("Identifier: " + identifier);
	}
}
