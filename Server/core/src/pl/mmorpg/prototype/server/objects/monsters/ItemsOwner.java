package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.items.Item;

public interface ItemsOwner
{

	void addItem(Item item);
	
	void addItems(Collection<Item> items);

	Collection<Item> getItems();
	
	void removeItem(long id);

}