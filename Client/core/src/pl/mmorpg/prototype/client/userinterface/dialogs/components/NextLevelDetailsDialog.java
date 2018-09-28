package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.LevelCalculator;

public class NextLevelDetailsDialog extends MouseHoverVisibleDialog
{
	private static final LevelCalculator levelCalculator = new LevelCalculator();
	private final StringValueLabel<Long> nextLevelExperienceLabel;

	public NextLevelDetailsDialog(Actor sourceActor, long currentExperience)
	{
		super(sourceActor, "Details");

		nextLevelExperienceLabel = new StringValueLabel<>("Next level experience: ",
				Settings.DEFAULT_SKIN, levelCalculator.getNextLevelExperience(currentExperience));
		text(nextLevelExperienceLabel);
		row();
		pack();
	}

	public void update(long newExperienceAmount)
	{
		nextLevelExperienceLabel.setValue(levelCalculator.getNextLevelExperience(newExperienceAmount));
		pack();
	}
}
