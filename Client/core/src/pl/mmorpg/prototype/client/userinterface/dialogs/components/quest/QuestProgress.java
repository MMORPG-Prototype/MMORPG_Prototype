package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.quests.Progress;
import pl.mmorpg.prototype.client.quests.QuestTask;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class QuestProgress extends Table
{
    public QuestProgress(Progress progress)
    {
        this.setSkin(Settings.DEFAULT_SKIN);
        StringValueLabel<String> mainProgressLabel = new StringValueLabel<String>("Progress: ", getSkin(),
                progress.getHumanReadableInfo());
        this.add(mainProgressLabel);
        this.row();

        QuestTask currentTask = progress.getCurrentTask();
        StringValueLabel<String> taskLabel = new StringValueLabel<>("Current task: ", getSkin(),
                currentTask.getDescription());
        this.add(taskLabel);
        this.row();
        Label currentTaskProgressLabel = new Label(currentTask.getPercentFinished() + "%", getSkin());
        this.add(currentTaskProgressLabel);
        this.row();
        this.pack();
    }

}
