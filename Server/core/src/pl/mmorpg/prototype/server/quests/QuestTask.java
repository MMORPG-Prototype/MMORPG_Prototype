package pl.mmorpg.prototype.server.quests;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pl.mmorpg.prototype.server.quests.events.Event;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
@JsonInclude(Include.NON_EMPTY)
public interface QuestTask extends Serializable
{
    void addNextTask(QuestTask task);
    
    @JsonIgnore
    boolean isLastTaskInQuest();
    
    boolean shouldProcess(Event e);
    
    void process(Event e);

    @JsonIgnore
    boolean isFinished();
    
    Collection<QuestTask> getNextTasks();
    
}
