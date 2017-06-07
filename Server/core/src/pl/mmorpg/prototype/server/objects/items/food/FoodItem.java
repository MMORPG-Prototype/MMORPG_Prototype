package pl.mmorpg.prototype.server.objects.items.food;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.effects.FoodNaturalRegenerationEffect;
import pl.mmorpg.prototype.server.objects.items.StackableUseableItem;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public abstract class FoodItem extends StackableUseableItem
{
	private float regenerationTime;

	public FoodItem(long id, float regenerationTime)
	{
		this(id, 1, regenerationTime);
	}
	
	public FoodItem(long id, int count, float regenerationTime)
	{
		super(id, count);
		this.regenerationTime = regenerationTime;
	}

	@Override
	public void useItem(Monster target, PacketsSender packetSender)
	{
		target.addEffect(new FoodNaturalRegenerationEffect(target, regenerationTime, packetSender));
	}
}
