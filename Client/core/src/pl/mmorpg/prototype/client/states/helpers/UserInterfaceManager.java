package pl.mmorpg.prototype.client.states.helpers;

import pl.mmorpg.prototype.client.items.QuickAccesIcon;
import pl.mmorpg.prototype.client.userinterface.MousePointerToItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class UserInterfaceManager
{

	public static MousePointerToItem quickAccessFieldClicked(MousePointerToItem mousePointerToItem,
			InventoryField<QuickAccesIcon> quickAccessField)
	{
		//TODO
//		if (mousePointerToItem.item != null && !quickAccessField.hasItem())
//		{
//			quickAccessField.put(mousePointerToItem.item);
//			mousePointerToItem.item = null;
//		} else if (mousePointerToItem.item != null && quickAccessField.hasItem())
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
