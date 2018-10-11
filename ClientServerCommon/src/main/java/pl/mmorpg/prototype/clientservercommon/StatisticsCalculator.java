package pl.mmorpg.prototype.clientservercommon;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;

public class StatisticsCalculator
{
	public static Statistics calculateStatistics(MonsterProperties properties)
	{
		return new Statistics.Builder()
				.attackPower(calculateAttackPower(properties))
				.attackRange(calculateAttackRange(properties))
				.attackSpeed(calculateAttackSpeed(properties))
				.defense(calculateDefense(properties))
				.maxHp(calculateMaxHp(properties))
				.maxMp(calculateMaxMp(properties))
				.build();
	}

	public static int calculateAttackPower(MonsterProperties properties)
	{
		return properties.strength;
	}

	public static int calculateAttackRange(MonsterProperties properties)
	{
		return 40;
	}

	public static float calculateAttackSpeed(MonsterProperties properties)
	{
		return 2.0f;
	}

	public static int calculateDefense(MonsterProperties properties)
	{
		return properties.strength;
	}

	public static int calculateMaxHp(MonsterProperties properties)
	{
		return properties.strength * 20;
	}

	public static int calculateMaxMp(MonsterProperties properties)
	{
		return properties.intelligence * 20;
	}
}
