package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.effects.EffectBase;

public class CannotStackWithOtherEffectException extends GameException
{
	public CannotStackWithOtherEffectException(Effect firstEffect, Effect secondEffect)
	{
		super("Effects should be same type when stacking. First effect was " + firstEffect + ", second effect was " + secondEffect);
	}
}
