package pl.mmorpg.prototype.server.quests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

import java.io.Serializable;
import java.util.List;

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

	/**
	 * Processes event and returns packet that should be sent to player
	 * Can be null
	 */
    EventPacket process(Event e);
    
    List<QuestTask> getNextTasks();

    void setSourceTask(CharactersQuests sourceTask);

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
