package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.objects.monsters.ItemsOwner;

public abstract class Item
{
    private long id;
    protected ItemsOwner owner = null;    

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
    
    public ItemsOwner getOwner()
    {
    	return owner;
    }
    
    public void setOwner(ItemsOwner newOwner)
    {
    	owner = newOwner;
    }
    
    public boolean hasOwner()
    {
    	return owner != null;
    }
}
