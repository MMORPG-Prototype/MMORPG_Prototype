package pl.mmorpg.prototype.server.objects.monsters;

import lombok.Data;

@Data
public class MonsterProperties
{
	public int experienceGain;
	public int attackRange;
	public int attackPower;
	public int maxHp;
	public int maxMp;
	public int hp;
	public int mp;

	private MonsterProperties()
	{
	}

	public static class Builder
	{
		private MonsterProperties monsterProperties;

		public Builder()
		{
			monsterProperties = new MonsterProperties();
		}

		public Builder experienceGain(int experienceGain)
		{
			monsterProperties.experienceGain = experienceGain;
			return this;
		}

		public Builder attackRange(int attackRange)
		{
			monsterProperties.attackRange = attackRange;
			return this;
		}

		public Builder attackPower(int attackPower)
		{
			monsterProperties.attackPower = attackPower;
			return this;
		}

		public Builder maxMp(int maxMp)
		{
			monsterProperties.maxMp = maxMp;
			return this;
		}

		public Builder maxHp(int maxHp)
		{
			monsterProperties.maxHp = maxHp;
			return this;
		}

		public MonsterProperties build()
		{
			monsterProperties.hp = monsterProperties.maxHp;
			monsterProperties.mp = monsterProperties.maxMp;
			return monsterProperties;
		}
	}
}