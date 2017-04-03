package pl.mmorpg.prototype.client.states.helpers;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.states.dialogs.components.InventoryField;

public class InventoryManager
{

	public static Item inventoryFieldClicked(Item mouseHoldingItem, InventoryField inventoryField)
	{
		if (mouseHoldingItem == null && inventoryField.hasItem())
		{
			mouseHoldingItem = inventoryField.getItem();
			inventoryField.removeItem();
		} else if (mouseHoldingItem != null && inventoryField.hasItem())
		{
			Item newMouseItem = inventoryField.getItem();
			inventoryField.put(mouseHoldingItem);
			mouseHoldingItem = newMouseItem;
		} else if (mouseHoldingItem != null && !inventoryField.hasItem())
		{
			inventoryField.put(mouseHoldingItem);
			mouseHoldingItem = null;
		}
		return mouseHoldingItem;
	}

}
