package pl.mmorpg.prototype.server.quests;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface QuestTask extends Serializable
{
    void addNextTask(QuestTask task);

    /**
     * Returns index of parent.children collection of this quest task
     * Can be null
     */
    Integer getParentIndex();

    @JsonIgnore
    boolean isLastTaskInQuest();

    boolean shouldProcess(Event e);

    void process(Event e);
    
    Collection<QuestTask> getNextTasks();

    void setSourceTask(CharactersQuests sourceTask);

    void proceedToNextTasks();

    default void handleEvent(Event e, QuestFinishedObserver observer)
    {
        if (shouldProcess(e))
            process(e);
        if (isFinished())
        {
            proceedToNextTasks();
            if (isLastTaskInQuest())
                questFinished(observer);
        }
    }

    void questFinished(QuestFinishedObserver observer);

    @JsonIgnore
    String getDescription();

    @JsonIgnore
    float getPercentFinished();
    
    @JsonIgnore
    default boolean isFinished()
    {
        return getPercentFinished() >= 100.0f;
    }

}
