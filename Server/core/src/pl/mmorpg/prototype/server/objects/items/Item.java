package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;

public abstract class Item
{
    private final long id;
    private InventoryPosition inventoryPosition;

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
    
    public InventoryPosition getInventoryPosition()
    {
    	return inventoryPosition;
    }
    
    public void setInventoryPosition(InventoryPosition position)
    {
    	this.inventoryPosition = position;
    }
}
