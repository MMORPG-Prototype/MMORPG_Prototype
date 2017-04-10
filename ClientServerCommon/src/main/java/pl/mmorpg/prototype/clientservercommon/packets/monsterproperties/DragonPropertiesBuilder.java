package pl.mmorpg.prototype.clientservercommon.packets.monsterproperties;

public class DragonPropertiesBuilder extends MonsterProperties.Builder
{
	@Override
	public MonsterProperties build()
	{
		attackPower(10);
		attackRange(40);
		defense(2);
		experienceGain(100);
		maxHp(100);
		maxMp(0);
		attackSpeed(2.0f);
		return super.build();
	}
}
