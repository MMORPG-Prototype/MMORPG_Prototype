package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;

public class CannotEquipItemException extends GameException
{
	public CannotEquipItemException(EquipableItem equipableItem, EquipmentPosition equipmentPosition)
	{
		super("Cannot equip item: " + equipableItem + " on position " + equipmentPosition);
	}
}
