package pl.mmorpg.prototype.server.objects.effects.timed;

import pl.mmorpg.prototype.server.objects.effects.EffectBase;

public abstract class TimedEffect extends EffectBase<TimedEffect>
{
	private float activeTime;

	public TimedEffect(float activeTime)
	{
		this.activeTime = activeTime;
	}
	
	@Override
	public void update(float deltaTime)
	{
		activeTime -= deltaTime;
	}
	
	@Override
	public boolean shouldDeactivate()
	{
		return activeTime <= 0.0f;
	}
	
	@Override
	public void stackWithSameTypeEffect(TimedEffect effect)
	{
		activeTime += effect.activeTime;
	}
}
