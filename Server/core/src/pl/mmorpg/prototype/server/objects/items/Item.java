package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemTypes;

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
    
    public ItemTypes getIdentifier()
    {
        return ItemIdentifier.getIdentifier(getClass());
    }
}
