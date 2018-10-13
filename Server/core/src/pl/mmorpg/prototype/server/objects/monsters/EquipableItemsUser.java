package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.Item;

public interface EquipableItemsUser extends ItemsOwner
{
	Item getItem(EquipmentPosition equipmentPosition);

	default boolean canEquip(EquipableItem equipableItem, EquipmentPosition equipmentPosition)
	{
		return getItem(equipmentPosition) == null && equipableItem.getSuiteEquipmentPositions().contains(equipmentPosition);
	}

	void equip(EquipableItem equipableItem, EquipmentPosition equipmentPosition);

	boolean canTakeOff(EquipableItem equipableItem, InventoryPosition destinationPosition);

	void takeOff(EquipableItem equipableItem, InventoryPosition destinationPosition);
}
