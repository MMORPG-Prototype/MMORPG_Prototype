package pl.mmorpg.prototype.server.objects.effects.constant;

import pl.mmorpg.prototype.clientservercommon.StatisticsCalculator;
import pl.mmorpg.prototype.clientservercommon.StatisticsOperations;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;
import pl.mmorpg.prototype.server.objects.effects.Effect;
import pl.mmorpg.prototype.server.objects.effects.EffectBase;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public abstract class ConstantEffect extends EffectBase<ConstantEffect>
{
	private final Statistics deltaStatistics;
	private final Monster target;
	private boolean activated = false;
	private boolean toDeactivate = false;

	protected ConstantEffect(Monster target)
	{
		this.target = target;
		Statistics baseStatistics = StatisticsCalculator.calculateStatistics(target.getProperties());
		deltaStatistics = getDeltaModificationStatistics(baseStatistics);
	}

	protected abstract Statistics getDeltaModificationStatistics(Statistics baseStatistics);

	@Override
	public boolean canStackWith(Effect effect)
	{
		return false;
	}

	@Override
	public void stackWithOtherEffect(ConstantEffect effect)
	{
		throw new UnsupportedOperationException("Constant effects should not be stacked with each other "
				+ "(They could but this way its easier and clearer)");
	}

	@Override
	public void activate()
	{
		doActivate();
		activated = true;
	}

	private void doActivate()
	{
		target.modifyByDeltaStatistics(deltaStatistics);
	}

	@Override
	public void deactivate()
	{
		if (activated)
		{
			doDeactivate();
			toDeactivate = true;
			activated = false;
		}
	}

	private void doDeactivate()
	{
		target.modifyByDeltaStatistics(StatisticsOperations.negate(deltaStatistics));
	}

	@Override
	public void update(float deltaTime)
	{
	}

	@Override
	public boolean shouldDeactivate()
	{
		return toDeactivate;
	}
}
