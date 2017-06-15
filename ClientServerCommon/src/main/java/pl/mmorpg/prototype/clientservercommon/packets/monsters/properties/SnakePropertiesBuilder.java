package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

public class SnakePropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		 attackPower(7)
		.attackRange(30)
		.defense(1)
		.experienceGain(100)
		.maxHp(40)
		.maxMp(0)
		.hpAndMpFull()
		.attackSpeed(2.0f)
		.level(1);
		return super.build();
	}
}
