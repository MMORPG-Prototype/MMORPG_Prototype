package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.LevelCalculator;

public class LevelUpProgressBar extends ProgressBar
{
	private static final LevelCalculator levelCalculator = new LevelCalculator();

	private final NextLevelDetailsDialog nextLevelDetailsDialog;

	public LevelUpProgressBar(long currentExperience, Stage containerForPopUpDialog)
	{
		super(levelCalculator.getCurrentLevelMinExperience(currentExperience),
				levelCalculator.getCurrentLevelMaxExperience(currentExperience),
				1, false,
				Settings.DEFAULT_SKIN);
		setAnimateDuration(0.1f);
		setValue(currentExperience);

		nextLevelDetailsDialog = new NextLevelDetailsDialog(this, currentExperience);
		containerForPopUpDialog.addActor(nextLevelDetailsDialog);
	}

	public void updateExperience(long newExperienceAmount)
	{
		long experience = (long) getValue();
		int currentLevel = levelCalculator.getLevel(experience);
		if (levelCalculator.doesQualifyForLevelingUp(currentLevel, newExperienceAmount)) {
			super.setRange(levelCalculator.getExperience(currentLevel + 1),  levelCalculator.getExperience(currentLevel + 2));
			nextLevelDetailsDialog.update(newExperienceAmount);
		}
		setValue(newExperienceAmount);
	}
}
