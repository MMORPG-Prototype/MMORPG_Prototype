package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public class UnknownItemTypeException extends GameException
{

    public UnknownItemTypeException(ItemIdentifiers itemType)
    {
        this(itemType.toString());
    }

	public UnknownItemTypeException(String identifier)
	{
		super("ItemType: " + identifier);
	}

}
