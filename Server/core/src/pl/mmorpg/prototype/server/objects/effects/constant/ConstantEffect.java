package pl.mmorpg.prototype.server.objects.effects.constant;

import pl.mmorpg.prototype.server.objects.effects.EffectBase;

public class ConstantEffect extends EffectBase<ConstantEffect>
{
	@Override
	public void stackWithSameTypeEffect(ConstantEffect effect)
	{
	}

	@Override
	public void activate()
	{

	}

	@Override
	public void deactivate()
	{
	}

	@Override
	public void update(float deltaTime)
	{

	}

	@Override
	public boolean shouldDeactivate()
	{
		return false;
	}
}
