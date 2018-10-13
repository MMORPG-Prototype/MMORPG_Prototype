package pl.mmorpg.prototype.server.objects.effects;

import pl.mmorpg.prototype.server.exceptions.CannotStackWithOtherEffectException;

public abstract class EffectBase<T> implements Effect
{
	@SuppressWarnings("unchecked")
    @Override
	public void stackWithOtherEffect(Effect effect)
	{
		if(!canStackWith(effect))
			throw new CannotStackWithOtherEffectException(this, effect);
		stackWithOtherEffect((T)effect);
	}

	@Override
	public boolean canStackWith(Effect effect)
	{
		return isSameType(effect);
	}

	private boolean isSameType(Effect effect)
	{
		return this.getClass().equals(effect.getClass());
	}

	public abstract void stackWithOtherEffect(T effect);
	
	
}
