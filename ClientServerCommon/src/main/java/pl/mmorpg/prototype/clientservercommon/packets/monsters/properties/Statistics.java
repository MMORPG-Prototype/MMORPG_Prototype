package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
@Data
public class Statistics
{
	public int attackRange;
	public int attackPower;
	public float attackSpeed;
	public int defense;
	public int maxHp;
	public int maxMp;

	private Statistics()
	{
	}

	public static class Builder
	{
		private final Statistics statistics = new Statistics();

		public Builder attackRange(int attackRange)
		{
			statistics.attackRange = attackRange;
			return this;
		}

		public Builder attackPower(int attackPower)
		{
			statistics.attackPower = attackPower;
			return this;
		}

		public Builder attackSpeed(float attackSpeed)
		{
			statistics.attackSpeed = attackSpeed;
			return this;
		}

		public Builder defense(int defense)
		{
			statistics.defense = defense;
			return this;
		}

		public Builder maxMp(int maxMp)
		{
			statistics.maxMp = maxMp;
			return this;
		}

		public Builder maxHp(int maxHp)
		{
			statistics.maxHp = maxHp;
			return this;
		}

		public Statistics build()
		{
			return statistics;
		}
	}
}
