package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.ItemTypes;

public class UnknownItemTypeException extends GameException
{

    public UnknownItemTypeException(ItemTypes itemType)
    {
        super("ItemType: " + itemType);
    }

}
