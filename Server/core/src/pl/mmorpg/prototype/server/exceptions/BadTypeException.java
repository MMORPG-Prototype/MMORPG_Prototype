package pl.mmorpg.prototype.server.exceptions;

public class BadTypeException extends GameException
{
	public BadTypeException(String expected, String actual)
	{
		super("Bad type, " + expected + " expected, but got " + actual);
	}

}
