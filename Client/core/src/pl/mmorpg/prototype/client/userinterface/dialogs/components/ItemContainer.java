package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import pl.mmorpg.prototype.client.items.Item;

public interface ItemContainer
{

	void put(Item item);

	boolean hasItem();

	Item getItem();

	void removeItem();
}