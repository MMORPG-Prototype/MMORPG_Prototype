package pl.mmorpg.prototype.client.exceptions;

import pl.mmorpg.prototype.client.objects.icons.items.Item;

public class CannotUseThisItemException extends GameException
{

	public CannotUseThisItemException(Item item)
	{
		super(item.toString());
	}

}
