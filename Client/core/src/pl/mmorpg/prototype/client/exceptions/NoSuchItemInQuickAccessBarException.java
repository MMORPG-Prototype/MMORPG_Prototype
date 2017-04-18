package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.items.Item;

public class NoSuchItemInQuickAccessBarException extends GameException
{
	public NoSuchItemInQuickAccessBarException(Item usedItem)
	{
		super("Item: " + usedItem.getIdentifier());
	}

}
