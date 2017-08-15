package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.exceptions.ItemHasNoPositionException;
import pl.mmorpg.prototype.server.objects.monsters.ItemsOwner;

public abstract class Item
{
    private final long id;
    protected ItemsOwner owner = null;
    private InventoryPosition inventoryPosition = null;

    public Item(long id)
    {
        this.id = id;
    }
    
    public Item(long id, InventoryPosition inventoryPosition)
    {
    	this(id);
    	this.inventoryPosition = inventoryPosition; 
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
    
    public InventoryPosition getInventoryPosition()
    {
    	if(inventoryPosition == null)
    		throw new ItemHasNoPositionException(ItemIdentifier.getIdentifier(getClass()));
    	return inventoryPosition;
    }
    
    public void setInventoryPosition(InventoryPosition position)
    {
    	this.inventoryPosition = position;
    }
}
