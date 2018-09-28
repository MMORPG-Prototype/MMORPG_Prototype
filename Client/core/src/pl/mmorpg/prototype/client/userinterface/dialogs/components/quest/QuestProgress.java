package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.exceptions.QuestFinishedException;
import pl.mmorpg.prototype.client.quests.Progress;
import pl.mmorpg.prototype.client.quests.QuestTask;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;

public class QuestProgress extends Table
{
    public QuestProgress(Progress progress)
    {
        this.setSkin(Settings.DEFAULT_SKIN);
        addMainProgressInfo(progress);
        addCurrentTaskProgressInfo(progress);
        this.pack();
    }

    private void addCurrentTaskProgressInfo(Progress progress)
    {
        try
        {
            QuestTask currentTask = progress.getCurrentTask();
            StringValueLabel<String> taskLabel = new StringValueLabel<>("Current task: ", getSkin(),
                    currentTask.getDescription());
            this.add(taskLabel);
            this.row();
            Label currentTaskProgressLabel = new Label((int)currentTask.getPercentFinished() + "%", getSkin());
            this.add(currentTaskProgressLabel);
            this.row();
        }
        catch(QuestFinishedException e)
        {
            Label currentTaskProgressLabel = new Label("Quest finished", getSkin());
            this.add(currentTaskProgressLabel);
            this.row();
        }
    }

    private void addMainProgressInfo(Progress progress)
    {
        StringValueLabel<String> mainProgressLabel = new StringValueLabel<>("Progress: ", getSkin(),
                progress.getHumanReadableInfo());
        this.add(mainProgressLabel);
        this.row();
    }

}
