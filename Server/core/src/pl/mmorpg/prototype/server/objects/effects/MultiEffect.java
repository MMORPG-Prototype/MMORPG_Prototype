package pl.mmorpg.prototype.server.objects.effects;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class MultiEffect extends EffectBase<MultiEffect>
{
	private Map<Class<? extends Effect>, MultiEffectWrapper> effects;

	protected MultiEffect(Collection<Effect> effects)
	{
		this.effects = effects
				.stream()
				.map( e -> new MultiEffectWrapper(e))
				.collect(Collectors.toMap( e -> e.getClass(),  e -> e));
	}
	
	@Override
	public void activate()
	{
		effects.values().forEach(e -> e.activate());
	}

	@Override
	public void deactivate()
	{	
		effects.values().forEach( e -> e.deactivate());
	}

	@Override
	public void update(float deltaTime)
	{
		effects.values().forEach( e -> e.update(deltaTime));
	}

	@Override
	public boolean shouldDeactivate()
	{
		return effects
				.values()
				.stream()
				.allMatch( e -> !e.isEffectActivated());
	}
	
	@Override
	public void stackWithSameTypeEffect(MultiEffect effect)
	{
		this.effects.forEach( (key, value) ->
		{
			MultiEffectWrapper sameTypeEffect = effect.effects.get(key);
			value.stackWithSameTypeEffect(sameTypeEffect);
		});
	}

}
