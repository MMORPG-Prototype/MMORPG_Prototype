package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.userinterface.dialogs.components.ItemQuickAccessIcon;

public class NoSuchItemInQuickAccessBarException extends GameException
{
	public NoSuchItemInQuickAccessBarException(ItemQuickAccessIcon usedItem)
	{
		super("Item: " + usedItem.getItemIdenfier());
	}

}
