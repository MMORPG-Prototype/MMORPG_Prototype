package pl.mmorpg.prototype.server.exceptions;

public class NoSuchItemToRemoveException extends GameException
{

	public NoSuchItemToRemoveException(long id)
	{
		super("Item id: " + id);
	}

}
