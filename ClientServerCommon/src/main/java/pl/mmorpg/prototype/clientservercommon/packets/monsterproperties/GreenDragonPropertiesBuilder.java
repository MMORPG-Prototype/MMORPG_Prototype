package pl.mmorpg.prototype.clientservercommon.packets.monsterproperties;

public class GreenDragonPropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		 attackPower(10)
		.attackRange(40)
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
