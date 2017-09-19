package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.userinterface.dialogs.components.QuickAccessIcon;

public class NoSuchItemInQuickAccessBarException extends GameException
{
	public NoSuchItemInQuickAccessBarException(QuickAccessIcon usedItem)
	{
		super("Item: " + usedItem.getItemIdenfier());
	}

}
