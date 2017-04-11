package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.objects.items.Item;

public class CannotUseThisItemException extends GameException
{

    public CannotUseThisItemException(Item characterItem)
    {
        super("Unuseable item: " + characterItem.getIdentifier());
    }

}
