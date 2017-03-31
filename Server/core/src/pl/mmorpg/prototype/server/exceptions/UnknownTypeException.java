package pl.mmorpg.prototype.server.exceptions;

public class UnknownTypeException extends GameException
{

	public UnknownTypeException(String type)
	{
		super("The type in factory is unknown: " + type);
	}

}
