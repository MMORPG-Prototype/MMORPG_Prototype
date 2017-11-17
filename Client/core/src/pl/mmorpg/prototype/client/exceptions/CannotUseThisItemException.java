package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.items.ItemIcon;

public class CannotUseThisItemException extends GameException
{

	public CannotUseThisItemException(ItemIcon item)
	{
		super(item.toString());
	}

}
