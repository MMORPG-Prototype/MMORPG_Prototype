package pl.mmorpg.prototype.server.objects.items.equipment;

import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.effects.constant.item.SwordEffect;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

import java.util.Collection;
import java.util.Collections;

public class Sword extends EquipableItem
{
	public Sword(long id)
	{
		super(id);
	}

	@Override
	public Effect createStatisticsModificationEffect(Monster target)
	{
		return new SwordEffect(target);
	}

	@Override
	public Collection<EquipmentPosition> getSuiteEquipmentPositions()
	{
		return Collections.singleton(EquipmentPosition.RIGHT_HAND);
	}
}
