package pl.mmorpg.prototype.server.exceptions;

public class NoSuchItemToRetrieveException extends GameException
{
	public NoSuchItemToRetrieveException(long itemId)
	{
		super("Item id: " + itemId);
	}	
}
