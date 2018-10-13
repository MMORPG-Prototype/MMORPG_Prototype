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

	default boolean containsItem(long itemId)
	{
		return getItem(itemId) == null;
	}

	void removeItem(long id);

}