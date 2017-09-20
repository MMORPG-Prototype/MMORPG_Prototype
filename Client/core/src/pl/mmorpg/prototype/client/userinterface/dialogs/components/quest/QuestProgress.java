package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import pl.mmorpg.prototype.client.quests.Progress;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class QuestProgress extends StringValueLabel<String>
{
    public QuestProgress(Progress progress)
    {
        super("Progress", Settings.DEFAULT_SKIN, progress.getHumanReadableInfo());
    }

}
