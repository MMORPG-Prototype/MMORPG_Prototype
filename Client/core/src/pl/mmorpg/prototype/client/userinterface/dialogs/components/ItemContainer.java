package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemReference;

public interface ItemContainer
{
	void put(ItemReference item);

	boolean hasItem();

	Item getItem();

	void removeItem();
}