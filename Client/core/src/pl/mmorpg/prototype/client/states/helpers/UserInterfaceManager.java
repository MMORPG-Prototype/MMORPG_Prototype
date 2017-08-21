package pl.mmorpg.prototype.client.states.helpers;

import pl.mmorpg.prototype.client.items.DraggableItem;
import pl.mmorpg.prototype.client.items.QuickAccessIcon;
import pl.mmorpg.prototype.client.userinterface.MousePointerToItem;
import pl.mmorpg.prototype.client.userinterface.dialogs.ItemCounter;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class UserInterfaceManager
{

	public static MousePointerToItem quickAccessFieldClicked(MousePointerToItem mousePointerToItem,
			InventoryField<QuickAccessIcon> quickAccessField, ItemCounter itemCounter)
	{
		DraggableItem heldItem = mousePointerToItem.item;
		if (heldItem != null)
		{
			QuickAccessIcon icon = new QuickAccessIcon(heldItem.getIdentifier(), itemCounter);
			quickAccessField.put(icon);
			mousePointerToItem.item = null;
		}
		else if(quickAccessField.hasContent())
				quickAccessField.removeContent();
		
		return mousePointerToItem;
	}

}
