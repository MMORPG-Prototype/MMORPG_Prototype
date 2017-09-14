package pl.mmorpg.prototype.server.quests;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import pl.mmorpg.prototype.server.quests.events.Event;

public abstract class QuestTaskBase<T extends Event> implements QuestTask
{
    @JsonProperty
    private Collection<QuestTask> nextTasks = new ArrayList<>(1);
    
    private Class<T> desiredEventType;

    public QuestTaskBase(Class<T> desiredEventType)
    {
        this.desiredEventType = desiredEventType;
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
    public Collection<QuestTask> getNextTasks()
    {
        return nextTasks;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean shouldProcess(Event e)
    {
        return e.getClass().equals(desiredEventType) && isApplicable((T)e);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void process(Event e)
    {
        apply((T)e);
    }
    
    public abstract boolean isApplicable(T event);
    
    public abstract void apply(T event);
    
}
