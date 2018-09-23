package pl.mmorpg.prototype.clientservercommon;

public class LevelCalculator
{
	public long getExperience(int level)
	{
		return Math.round(Math.pow(level, Math.sqrt(level / 16f)) * 300f) - 300;
	}

	public int getLevel(long experience)
	{
		int level = 1;
		while (experience >= getExperience(level))
			level++;
		return level;
	}

	public boolean doesQualifyForLevelingUp(int currentLevel, long currentExperience, long experienceGain)
	{
		return doesQualifyForLevelingUp(currentLevel, currentExperience + experienceGain);
	}

	public boolean doesQualifyForLevelingUp(int currentLevel, long newExperienceAmount)
	{
		return currentLevel < getLevel(newExperienceAmount);
	}

	public long getCurrentLevelMinExperience(long currentExperience)
	{
		return getExperience(getLevel(currentExperience));
	}

	public long getCurrentLevelMaxExperience(long currentExperience)
	{
		return getNextLevelExperience(currentExperience);
	}

	public long getNextLevelExperience(long currentExperience)
	{
		return getExperience(getLevel(currentExperience) + 1);
	}
}
