package pl.mmorpg.prototype.client.userinterface.dialogs.components.quest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.client.quests.Quest;

public class QuestListPane extends ScrollPane
{
    private final QuestList questList;
    private final Runnable scrollingTask = () -> setScrollPercentY(1.0f);

    public QuestListPane()
    {
        super(new QuestList());
        questList = (QuestList) getWidget();
        setSmoothScrolling(false); 
        setTransform(true);
    }
    
    public void addQuest(Quest quest)
    {
        questList.addQuest(quest);
        Gdx.app.postRunnable(scrollingTask);
    }

}
