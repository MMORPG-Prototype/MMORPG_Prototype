package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.quest.QuestListPane;

public class QuestListDialog extends Dialog
{
    private QuestListPane questListPane;

    public QuestListDialog()
    {
        super("Quests", Settings.DEFAULT_SKIN);
        questListPane = new QuestListPane();
        this.add(questListPane);
    }
    
    public void addQuest(Quest quest)
    {
        questListPane.addQuest(quest);
        this.pack();
    }

}
