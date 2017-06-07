package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

public class NpcDefaultPropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		 attackPower(100)
		.attackRange(100)
		.defense(100)
		.experienceGain(10000)
		.maxHp(1000)
		.maxMp(500)
		.hpAndMpFull()
		.attackSpeed(5.0f)
		.level(10);
		return super.build();
	}
}
