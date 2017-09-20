package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class QuestElement extends Table
{
    private final QuestProgress questProgress;

    public QuestElement(Quest quest)
    {
        Label questNameLabel = new Label(quest.getQuestName(), Settings.DEFAULT_SKIN);
        this.add(questNameLabel);
        this.row();
        Label questDescriptionLabel = new Label(quest.getDescription(), Settings.DEFAULT_SKIN);
        this.add(questDescriptionLabel);
        this.row();
        questProgress = new QuestProgress(quest.getProgress());
        this.add(questProgress);
        this.row();
        this.pack();
    }
}
