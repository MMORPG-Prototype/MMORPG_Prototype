package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public abstract class Item
{
    private long id;

    public Item(long id)
    {
        this.id = id;
    }
    
    public long getId()
    {
        return id;
    }
    
    public ItemIdentifiers getIdentifier()
    {
        return ItemIdentifier.getIdentifier(getClass());
    }
}
