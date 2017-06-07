package pl.mmorpg.prototype.server.objects.effects;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class FoodNaturalRegenerationEffect extends MultiEffect
{
	private Monster eater;

	public FoodNaturalRegenerationEffect(Monster eater, float activeTime)
	{
		super(getEffects(eater, activeTime));
	}
	
	private static Collection<Effect> getEffects(Monster target, float activeTime)
	{
		Collection<Effect> effects = new LinkedList<>();
		effects.add(new HpRegenerationEffect(activeTime, 3.0f, 1, target));
		effects.add(new MpRegenerationEffect(activeTime, 2.5f, 1, target));
		return effects;
	}
	
}
