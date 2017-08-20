package pl.mmorpg.prototype.client.states.helpers;

import pl.mmorpg.prototype.client.items.DraggableItem;
import pl.mmorpg.prototype.client.items.QuickAccesIcon;
import pl.mmorpg.prototype.client.userinterface.MousePointerToItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class UserInterfaceManager
{

	public static MousePointerToItem quickAccessFieldClicked(MousePointerToItem mousePointerToItem,
			InventoryField<QuickAccesIcon> quickAccessField, ItemCounter itemCounter)
	{
		//TODO
		DraggableItem heldItem = mousePointerToItem.item;
		if (heldItem != null && !quickAccessField.hasContent())
		{
			quickAccessField.getContent();
			QuickAccesIcon icon = new QuickAccesIcon(heldItem.getIdentifier(), itemCounter);
			quickAccessField.put(icon);
			icon.setItemIdenfier(heldItem.getIdentifier());
			icon.recalculateItemNumber();
			mousePointerToItem.item = null;
		} //else if (mousePointerToItem.item != null && quickAccessField.hasContent())
//		{
//			Item item = quickAccessField.getItem();
//			quickAccessField.removeItem();
//			quickAccessField.put(mousePointerToItem.item);
//			mousePointerToItem.item = (DraggableItem)item;
//		} else if (mousePointerToItem.item == null && quickAccessField.hasItem())
//		{
//			mousePointerToItem.item = (DraggableItem)quickAccessField.getItem();
//			quickAccessField.removeItem();
//		}
//		if (mousePointerToItem.item != null)
//			mousePointerToItem.itemSource = ItemSources.QUICK_ACCESS_BAR;
		return mousePointerToItem;
	}

}
