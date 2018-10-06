package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.quests.Quest;

public class QuestList extends Table
{
    private final Map<String, QuestElement> quests = new HashMap<>();
    
    public void addQuest(Quest quest)
    {
        QuestElement questElement = new QuestElement(quest);
        quests.put(quest.getQuestName(), questElement);
        this.add(questElement);
        this.row();
    }

    public void updateQuest(Quest quest)
    {
        QuestElement questElement = quests.get(quest.getQuestName());
        questElement.updateData(quest);
    }
}
