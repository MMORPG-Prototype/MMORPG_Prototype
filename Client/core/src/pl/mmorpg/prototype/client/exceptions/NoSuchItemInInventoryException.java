package pl.mmorpg.prototype.client.exceptions;

public class NoSuchItemInInventoryException extends GameException
{

	public NoSuchItemInInventoryException(long itemId)
	{
		super("Item id: " + itemId);
	}

}
