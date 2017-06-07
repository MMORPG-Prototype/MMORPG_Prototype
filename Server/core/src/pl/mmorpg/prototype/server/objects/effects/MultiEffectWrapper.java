package pl.mmorpg.prototype.server.objects.effects;

public class MultiEffectWrapper implements Effect
{
	Effect effect;
	boolean isActivated = false;
	
	MultiEffectWrapper(Effect effect)
	{
		this.effect = effect;
	}
	
	boolean isEffectActivated()
	{
		return isActivated;
	}

	@Override
	public void activate()
	{
		effect.activate();
		isActivated = true;	
	}

	@Override
	public void deactivate()
	{
		effect.deactivate();
		isActivated = false;
	}

	@Override
	public void update(float deltaTime)
	{
		effect.update(deltaTime);
	}

	@Override
	public boolean shouldDeactivate()
	{
		return effect.shouldDeactivate();
	}

	@Override
	public void stackWithSameTypeEffect(Effect effect)
	{
		this.effect.stackWithSameTypeEffect(effect);
	}

}
