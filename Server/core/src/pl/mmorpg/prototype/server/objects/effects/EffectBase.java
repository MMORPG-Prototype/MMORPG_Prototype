package pl.mmorpg.prototype.server.objects.effects;

import pl.mmorpg.prototype.server.exceptions.DifferentTypeEffectException;

public abstract class EffectBase<T> implements Effect
{
	@SuppressWarnings("unchecked")
    @Override
	public void stackWithSameTypeEffect(Effect effect)
	{
		if(!isSameType(effect))
			throw new DifferentTypeEffectException();
		stackWithSameTypeEffect((T)effect);
	}

	private boolean isSameType(Effect effect)
	{
		return effect.getClass().equals(effect.getClass());
	}
	
	public abstract void stackWithSameTypeEffect(T effect);
	
	
}
