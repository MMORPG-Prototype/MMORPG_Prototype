package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public class UnknownItemTypeException extends GameException
{

    public UnknownItemTypeException(ItemIdentifiers itemType)
    {
        super("ItemType: " + itemType);
    }

}
