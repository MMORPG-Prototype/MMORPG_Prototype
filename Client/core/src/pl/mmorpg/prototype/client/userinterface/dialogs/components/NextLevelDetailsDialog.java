package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import pl.mmorpg.prototype.clientservercommon.LevelCalculator;

public class NextLevelDetailsDialog extends MouseHoverVisibleDialog
{
	private static final LevelCalculator levelCalculator = new LevelCalculator();

	public NextLevelDetailsDialog(Actor sourceActor, long currentExperience)
	{
		super(sourceActor, "Details");

		text("Next level experience: " + levelCalculator.getNextLevelExperience(currentExperience)).left();
		row();
		pack();
	}
}
