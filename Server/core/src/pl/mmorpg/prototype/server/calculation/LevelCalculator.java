package pl.mmorpg.prototype.server.calculation;

public class LevelCalculator
{

	public long getExperience(int level)
	{
		return Math.round(Math.pow(level, Math.sqrt(level/16f))*300f) - 300;
	}

	public long getLevel(int experience)
	{
		int level = 1;
		while (experience >= getExperience(level))
			level++;
		return level;
	}

	public boolean doesQualifyForLevelingUp(int currentLevel, int currentExperience, int experienceGain)
	{
		return currentLevel < getLevel(currentExperience + experienceGain);
	}
}
