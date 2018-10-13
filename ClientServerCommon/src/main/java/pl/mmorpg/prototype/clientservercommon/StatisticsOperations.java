package pl.mmorpg.prototype.clientservercommon;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;

public class StatisticsOperations
{
	public static void modifyByDeltaStatistics(Statistics toModify, Statistics deltaStatistics)
	{
		toModify.maxHp += deltaStatistics.maxHp;
		toModify.maxMp += deltaStatistics.maxMp;
		toModify.attackRange += deltaStatistics.attackRange;
		toModify.attackSpeed += deltaStatistics.attackSpeed;
		toModify.attackPower += deltaStatistics.attackPower;
		toModify.defense += deltaStatistics.defense;
	}

	public static Statistics negate(Statistics value)
	{
		return new Statistics.Builder()
				.maxHp(-value.maxHp)
				.maxMp(-value.maxMp)
				.defense(-value.defense)
				.attackRange(-value.attackRange)
				.attackPower(-value.attackPower)
				.attackSpeed(-value.attackSpeed)
				.build();
	}
}
