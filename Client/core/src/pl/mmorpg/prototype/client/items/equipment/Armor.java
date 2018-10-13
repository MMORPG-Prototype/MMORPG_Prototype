package pl.mmorpg.prototype.client.items.equipment;

import pl.mmorpg.prototype.client.resources.Assets;

public class Armor extends EquipmentItem
{
	public Armor(long id)
	{
		super(Assets.get("Items/metal-chest-plate.png"), id);
	}
}
