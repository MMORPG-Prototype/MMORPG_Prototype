package pl.mmorpg.prototype.server.exceptions;

public class GameException extends RuntimeException
{
	public GameException()
	{
	}

	public GameException(String message)
	{
		super(message);
	}

	public GameException(String message, Throwable e) {
		super(message, e);
	}

}
