package pl.mmorpg.prototype.clientservercommon.packets.monsterproperties;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class MonsterProperties
{
	public int experienceGain;
	public int attackRange;
	public int attackPower;
	public float attackSpeed;
	public int defense;
	public int maxHp;
	public int maxMp;
	public int hp;
	public int mp;
	public int dexitirity;
	public int strength;
	public int magic;
	public int experience;
	public int gold;
	public int level;

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
		
		public Builder attackSpeed(float attackSpeed)
		{
			monsterProperties.attackSpeed = attackSpeed;
			return this;
		}
		
		public Builder defense(int defense)
		{
			monsterProperties.defense = defense;
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
		
		public Builder strength(int strength)
		{
			monsterProperties.strength = strength;
			return this;
		}
		
		public Builder magic(int magic)
		{
			monsterProperties.magic = magic;
			return this;
		}

		public Builder dexitirity(int dexitirity)
		{
			monsterProperties.dexitirity = dexitirity;
			return this;
		}
		
		public Builder hp(int hp)
		{
			monsterProperties.hp = hp;
			return this;
		}
		
		public Builder mp(int mp)
		{
			monsterProperties.mp = mp;
			return this;
		}
		
		public Builder hpAndMpFull()
		{
			monsterProperties.hp = monsterProperties.maxHp;
			monsterProperties.mp = monsterProperties.maxMp;
			return this;
		}
		
		public Builder experience(int experience)
		{
			monsterProperties.experience = experience;
			return this;
		}
		
		public Builder gold(int gold)
		{
			monsterProperties.gold = gold;
			return this;
		}
		
		public Builder level(int level)
		{
			monsterProperties.level = level;
			return this;
		}

		public MonsterProperties build()
		{
			return monsterProperties;
		}
	}
}
