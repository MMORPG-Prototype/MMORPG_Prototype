package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public class ItemHasNoPositionException extends GameException
{

	public ItemHasNoPositionException(ItemIdentifiers identifier)
	{
		System.out.println("Type: " + identifier);
	}

}
