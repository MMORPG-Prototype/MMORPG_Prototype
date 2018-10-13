package pl.mmorpg.prototype.server.objects.items.equipment;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.effects.constant.item.ArmorEffect;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

import java.util.Collection;
import java.util.Collections;

public class Armor extends EquipableItem
{
	public Armor(long id)
	{
		super(id);
	}

	@Override
	public Effect createStatisticsModificationEffect(Monster target)
	{
		return new ArmorEffect(target);
	}

	@Override
	public Collection<EquipmentPosition> getSuiteEquipmentPositions()
	{
		return Collections.singleton(EquipmentPosition.CHEST);
	}
}
