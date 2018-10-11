package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.server.database.entities.components.EquipmentPosition;

public class EquipableItem extends Item
{
	private EquipmentPosition equipmentPosition = EquipmentPosition.NONE;

	public EquipableItem(long id)
	{
		super(id);
	}

	public EquipmentPosition getEquipmentPosition()
	{
		return equipmentPosition;
	}

	public void setEquipmentPosition(EquipmentPosition equipmentPosition)
	{
		this.equipmentPosition = equipmentPosition;
	}
}
