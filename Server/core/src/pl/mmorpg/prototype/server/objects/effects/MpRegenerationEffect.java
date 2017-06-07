package pl.mmorpg.prototype.server.objects.effects;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class MpRegenerationEffect extends TimedEffectSelfUsedWithIntervals
{
	private int regenerationPower;
	private Monster target;

	public MpRegenerationEffect(float activeTime, float usageInterval, int regenerationPower, Monster target)
	{
		super(activeTime, usageInterval);
		this.regenerationPower = regenerationPower;
		this.target = target;
	}

	@Override
	public void oneTimeUsage()
	{
		MonsterProperties targetProperties = target.getProperties();
		if(targetProperties.mp + regenerationPower > targetProperties.maxMp)
			targetProperties.mp = targetProperties.maxMp;
		else
			targetProperties.mp += regenerationPower;
	}
}
