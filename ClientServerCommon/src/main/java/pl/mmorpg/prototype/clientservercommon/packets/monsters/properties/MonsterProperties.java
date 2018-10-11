package pl.mmorpg.prototype.clientservercommon.packets.monsters.properties;

import pl.mmorpg.prototype.clientservercommon.registering.Registerable;

@Registerable
public class MonsterProperties
{
	public int experienceGain;
	public int hp;
	public int mp;
	public int dexterity;
	public int strength;
	public int intelligence;
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
		
		public Builder strength(int strength)
		{
			monsterProperties.strength = strength;
			return this;
		}
		
		public Builder intelligence(int intelligence)
		{
			monsterProperties.intelligence = intelligence;
			return this;
		}

		public Builder dexterity(int dexterity)
		{
			monsterProperties.dexterity = dexterity;
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
