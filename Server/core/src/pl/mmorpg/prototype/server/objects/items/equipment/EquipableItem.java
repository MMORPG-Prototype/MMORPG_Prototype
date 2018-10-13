package pl.mmorpg.prototype.server.objects.items.equipment;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

import java.util.Collection;

public abstract class EquipableItem extends Item
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

	public abstract Effect createStatisticsModificationEffect(Monster target);

	public abstract Collection<EquipmentPosition> getSuiteEquipmentPositions();
}
