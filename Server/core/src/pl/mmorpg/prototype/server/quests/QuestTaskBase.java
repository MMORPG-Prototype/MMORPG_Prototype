package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class QuestTaskBase<T extends Event> implements QuestTask
{
	@QuestMakerIgnore
    private transient CharactersQuests sourceTask;
    
    @JsonProperty
    @QuestMakerIgnore
    private List<QuestTask> nextTasks = new ArrayList<>(1);
    
    @QuestMakerIgnore
    private final Class<T> desiredEventType;

    private final Integer parentIndex;

    public QuestTaskBase(Integer parentIndex, Class<T> desiredEventType)
    {
        this.desiredEventType = desiredEventType;
        this.parentIndex = parentIndex;
    }
    
    @Override
    public void addNextTask(QuestTask task)
    {
        nextTasks.add(task);
    }
    
    @Override
    public boolean isLastTaskInQuest()
    {
        return nextTasks.isEmpty();
    }
    
    @Override
    public void proceedToNextTasks()
    {
        Collection<QuestTaskWrapper> dbQuestTasks = new ArrayList<>();
        for (QuestTask task : nextTasks)
        {
            QuestTaskWrapper wrapper = new QuestTaskWrapper();
            wrapper.setCharactersQuests(sourceTask);
            wrapper.setQuestTask(task);
            dbQuestTasks.add(wrapper);
        }

        sourceTask.setQuestTasks(dbQuestTasks);
        String newFinishedQuestTasksPath = parentIndex == null ? "0" :
                sourceTask.getFinishedQuestTasksPath() + ',' + parentIndex;
        sourceTask.setFinishedQuestTasksPath(newFinishedQuestTasksPath);
    }
    
    @Override
    public List<QuestTask> getNextTasks()
    {
        return nextTasks;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean shouldProcess(Event e)
    {
        return desiredEventType.isInstance(e) && isApplicable((T)e);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public EventPacket process(Event e)
    {
        return apply((T)e);
    }
    
    public abstract boolean isApplicable(T event);

    /**
     * See {@link QuestTask#process(Event)}
     */
    public abstract EventPacket apply(T event);

    @Override
    public void setSourceTask(CharactersQuests sourceTask)
    {
        this.sourceTask = sourceTask;
    }

    @Override
    public void questFinished(QuestFinishedObserver observer)
    {
        observer.playerFinishedQuest(sourceTask.getCharacter().getId(), sourceTask.getQuest());
    }

    public Integer getParentIndex()
    {
        return parentIndex;
    }

    protected CharactersQuests getSourceTask()
    {
        return sourceTask;
    }
}
