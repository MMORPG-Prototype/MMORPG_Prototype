package pl.mmorpg.prototype.client.items.equipment;

import pl.mmorpg.prototype.client.resources.Assets;

public class Sword extends EquipmentItem
{
	public Sword(long id)
	{
		super(Assets.get("Items/metal-sword.png"), id);
	}
}
