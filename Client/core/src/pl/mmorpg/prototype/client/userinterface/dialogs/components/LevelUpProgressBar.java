package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.LevelCalculator;

public class LevelUpProgressBar extends ProgressBar
{
	private static final LevelCalculator levelCalculator = new LevelCalculator();

	public LevelUpProgressBar(long currentExperience, Stage containerForPopUpDialog)
	{
		super(levelCalculator.getCurrentLevelMinExperience(currentExperience),
				levelCalculator.getCurrentLevelMaxExperience(currentExperience),
				1, false,
				Settings.DEFAULT_SKIN);
		setAnimateDuration(0.1f);
		setValue(currentExperience);

		containerForPopUpDialog.addActor(new NextLevelDetailsDialog(this, currentExperience));
	}

	public void updateExperience(long newExperienceAmount)
	{
		long experience = (long) getValue();
		int currentLevel = levelCalculator.getLevel(experience);
		if (levelCalculator.doesQualifyForLevelingUp(currentLevel, newExperienceAmount))
			super.setRange(levelCalculator.getExperience(currentLevel),  levelCalculator.getExperience(currentLevel + 1));
		setValue(newExperienceAmount);
	}
}
