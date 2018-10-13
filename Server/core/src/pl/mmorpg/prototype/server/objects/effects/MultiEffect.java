package pl.mmorpg.prototype.server.objects.effects;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class MultiEffect extends EffectBase<MultiEffect>
{
	private Map<Class<? extends Effect>, EffectWrapper> effects;

	protected MultiEffect(Collection<Effect> effects)
	{
		this.effects = effects
				.stream()
				.collect(Collectors.toMap(Effect::getClass, EffectWrapper::new));
	}
	
	@Override
	public void activate()
	{
		effects.values().forEach( e -> 
		{
			if(!e.isEffectActivated()) 
				e.activate();
		});
	}

	@Override
	public void deactivate()
	{	
		effects.values().forEach( e -> 
		{
			if(e.isEffectActivated()) 
				e.deactivate();
		});
	}

	@Override
	public void update(float deltaTime)
	{
		effects.values().forEach( e -> 
		{
			if(e.isEffectActivated()) 
			{
				e.update(deltaTime);
				if(e.shouldDeactivate())
					e.deactivate();
			}
		});
	}

	@Override
	public boolean shouldDeactivate()
	{
		return effects.values()
				.stream()
				.noneMatch(EffectWrapper::isEffectActivated);
	}
	
	@Override
	public void stackWithOtherEffect(MultiEffect effect)
	{
		this.effects.forEach( (key, value) ->
		{
			EffectWrapper sameTypeEffect = effect.effects.get(key);
			value.stackWithOtherEffect(sameTypeEffect.getEffect());
		});
	}

}
