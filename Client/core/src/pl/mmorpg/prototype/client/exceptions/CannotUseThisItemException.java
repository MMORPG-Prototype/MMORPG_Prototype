package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.items.Item;

public class CannotUseThisItemException extends GameException
{

	public CannotUseThisItemException(Item item)
	{
		super(item.toString());
	}

}
