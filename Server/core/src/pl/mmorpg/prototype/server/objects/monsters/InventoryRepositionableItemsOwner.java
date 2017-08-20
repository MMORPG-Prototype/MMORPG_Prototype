package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.Item;

public interface InventoryRepositionableItemsOwner extends ItemsOwner
{
	Item getItemByPosition(InventoryPosition position);
	
	boolean hasItemInPosition(InventoryPosition position);
}
