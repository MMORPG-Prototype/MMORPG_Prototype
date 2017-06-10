package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties.Builder;

public class SkeletonPropertiesBuilder extends Builder
{
	@Override
	public MonsterProperties build()
	{
		 attackPower(10)
		.attackRange(50)
		.defense(2)
		.experienceGain(100)
		.maxHp(100)
		.maxMp(0)
		.hpAndMpFull()
		.attackSpeed(2.0f)
		.level(1);
		return super.build();
	}
}
