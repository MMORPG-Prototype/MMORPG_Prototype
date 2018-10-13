package pl.mmorpg.prototype.server.objects.effects;

public class EffectWrapper implements Effect
{
	private Effect effect;
	private boolean isActivated = false;
	
	EffectWrapper(Effect effect)
	{
		this.effect = effect;
	}

	Effect getEffect()
	{
		return effect;
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
	public boolean canStackWith(Effect effect)
	{
		return this.effect.canStackWith(effect);
	}

	@Override
	public void stackWithOtherEffect(Effect effect)
	{
		this.effect.stackWithOtherEffect(effect);
	}

}
