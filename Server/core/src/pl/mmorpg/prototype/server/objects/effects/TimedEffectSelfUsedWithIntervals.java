package pl.mmorpg.prototype.server.objects.effects;

public abstract class TimedEffectSelfUsedWithIntervals extends TimedEffect
{
	private float usageInterval;
	private float currentUsageTime = 0.0f;

	public TimedEffectSelfUsedWithIntervals(float activeTime, float usageInterval)
	{
		super(activeTime);
		this.usageInterval = usageInterval;
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		currentUsageTime += deltaTime;
		if(currentUsageTime >= usageInterval)
		{
			currentUsageTime = 0.0f;
			oneTimeUsage();
		}
	}
	
	public abstract void oneTimeUsage();

	@Override
	public void activate()
	{
	}

	@Override
	public void deactivate()
	{
	}


}
