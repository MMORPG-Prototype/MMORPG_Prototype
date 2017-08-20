package pl.mmorpg.prototype.client.states.helpers;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemReference;
import pl.mmorpg.prototype.client.userinterface.ItemSources;
import pl.mmorpg.prototype.client.userinterface.MousePointerToItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class UserInterfaceManager
{

	public static MousePointerToItem inventoryFieldClicked(MousePointerToItem mousePointerToItem,
			InventoryField inventoryField, InventoryDialog inventory, ItemInventoryPosition inventoryFieldPosition)
	{
		if (mousePointerToItem.item == null && inventoryField.hasItem())
		{
			mousePointerToItem.item = inventoryField.getItem();
		} else if (mousePointerToItem.item != null && inventoryField.hasItem())
		{
			Item newMouseItem = inventoryField.getItem();
			inventoryField.put(new ItemReference(mousePointerToItem.item));
			mousePointerToItem.item = newMouseItem;
		} else if (mousePointerToItem.item != null && !inventoryField.hasItem())
		{
			inventory.removeIfHas(mousePointerToItem.item);
//			inventoryField.put(new ItemReference(mousePointerToItem.item));
//			mousePointerToItem.item = null;
			
		}
		if (mousePointerToItem.item != null)
			mousePointerToItem.itemSource = ItemSources.INVENTORY;
		return mousePointerToItem;
	}

	public static MousePointerToItem quickAccessFieldClicked(MousePointerToItem mousePointerToItem,
			InventoryField quickAccessField, InventoryField itemSource)
	{
		if (mousePointerToItem.item != null && !quickAccessField.hasItem())
		{
			if (itemSource != null && mousePointerToItem.itemSource.equals(ItemSources.INVENTORY))
				itemSource.put(new ItemReference(mousePointerToItem.item));
			quickAccessField.put(new ItemReference(mousePointerToItem.item));
			mousePointerToItem.item = null;
		} else if (mousePointerToItem.item != null && quickAccessField.hasItem())
		{
			Item item = quickAccessField.getItem();
			quickAccessField.removeItem();
			quickAccessField.put(new ItemReference(mousePointerToItem.item));
			mousePointerToItem.item = item;
		} else if (mousePointerToItem.item == null && quickAccessField.hasItem())
		{
			mousePointerToItem.item = quickAccessField.getItem();
			quickAccessField.removeItem();
		}
		if (mousePointerToItem.item != null)
			mousePointerToItem.itemSource = ItemSources.QUICK_ACCESS_BAR;
		return mousePointerToItem;
	}

}
