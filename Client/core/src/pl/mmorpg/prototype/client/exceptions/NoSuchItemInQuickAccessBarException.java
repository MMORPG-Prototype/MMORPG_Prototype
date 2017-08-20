package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.items.QuickAccesIcon;

public class NoSuchItemInQuickAccessBarException extends GameException
{
	public NoSuchItemInQuickAccessBarException(QuickAccesIcon usedItem)
	{
		super("Item: " + usedItem.getItemIdenfier());
	}

}
