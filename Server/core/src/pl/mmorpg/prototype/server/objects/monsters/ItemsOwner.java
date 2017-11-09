package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;

public interface ItemsOwner
{
	void addItemAllowStacking(Item item);
	
	void addItemsAllowStacking(Collection<Item> items);
	
	void addItemDenyStacking(Item item);
	
	void addItemsDenyStacking(Collection<Item> items);

	Collection<Item> getItems();
	
	Item getItem(long itemId);
	
	void removeItem(long id);

}