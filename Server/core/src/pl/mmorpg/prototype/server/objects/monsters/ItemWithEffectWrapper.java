package pl.mmorpg.prototype.server.objects.monsters;

import lombok.Data;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;

@Data
public class ItemWithEffectWrapper
{
	private final EquipableItem item;
	private final Effect effect;
}
