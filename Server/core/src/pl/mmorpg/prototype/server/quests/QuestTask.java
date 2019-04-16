package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.data.entities.components.EntityQuestTask;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

import java.io.Serializable;
import java.util.List;

public interface QuestTask extends Serializable, EntityQuestTask
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

	/**
	 * Processes event and returns packet that should be sent to player
	 * Can be null
	 */
    EventPacket process(Event e);

    void proceedToNextTasks();

    default EventPacket handleEvent(Event e, QuestFinishedObserver observer)
    {
        if (shouldProcess(e)) {
        	EventPacket eventPacket = process(e);

			if (isFinished())
			{
				proceedToNextTasks();
				if (isLastTaskInQuest())
					questFinished(observer);
			}
			return eventPacket;
		}
        return null;
	}

    void questFinished(QuestFinishedObserver observer);

	List<QuestTask> getNextTasks();

	@SuppressWarnings("unchecked")
	default List<EntityQuestTask> getNextEntityTasks() {
		return (List<EntityQuestTask>)(List)getNextTasks();
	}

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
