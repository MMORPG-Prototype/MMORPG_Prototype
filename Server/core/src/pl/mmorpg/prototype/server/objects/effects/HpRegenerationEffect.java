package pl.mmorpg.prototype.server.objects.effects;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class HpRegenerationEffect extends TimedEffectSelfUsedWithIntervals
{
	private int regenerationPower;
	private Monster target;

	public HpRegenerationEffect(float activeTime, float usageInterval, int regenerationPower, Monster target)
	{
		super(activeTime, usageInterval);
		this.regenerationPower = regenerationPower;
		this.target = target;
	}

	@Override
	public void oneTimeUsage()
	{
		MonsterProperties targetProperties = target.getProperties();
		if(targetProperties.hp + regenerationPower > targetProperties.maxHp)
			targetProperties.hp = targetProperties.maxHp;
		else
			targetProperties.hp += regenerationPower;
	}
	
}
