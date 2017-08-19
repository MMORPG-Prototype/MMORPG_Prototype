package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

public class GrayDragonPropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		 attackPower(7)
		.attackRange(40)
		.defense(5)
		.experienceGain(100)
		.maxHp(200)
		.maxMp(0)
		.hpAndMpFull()
		.attackSpeed(2.0f)
		.level(1);
		return super.build();
	}
}
