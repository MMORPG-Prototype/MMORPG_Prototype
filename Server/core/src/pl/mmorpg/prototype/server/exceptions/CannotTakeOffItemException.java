package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;

public class CannotTakeOffItemException extends GameException
{
	public CannotTakeOffItemException(EquipableItem equipableItem, InventoryPosition destinationPosition)
	{
		super("Cannot take off equipable item " + equipableItem + ", destination position: " + destinationPosition);
	}
}
