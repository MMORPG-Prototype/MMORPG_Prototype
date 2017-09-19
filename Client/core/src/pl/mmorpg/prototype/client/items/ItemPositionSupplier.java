package pl.mmorpg.prototype.client.items;

public interface ItemPositionSupplier
{
    ItemInventoryPosition get(String itemIdentifier, int numberOfItems);
    
    default ItemInventoryPosition get(Item item)
    {
        if(item instanceof StackableItem)
            return get(item.getIdentifier(), 1);
        return get(item.getIdentifier(), ((StackableItem)item).getItemCount());
    }
}
