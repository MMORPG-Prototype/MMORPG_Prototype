package pl.mmorpg.prototype.server.quests;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pl.mmorpg.prototype.server.quests.events.Event;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface QuestTask extends Serializable
{
    void addNextTask(QuestTask task);
    
    boolean isLastTaskInQuest();
    
    boolean shouldProcess(Event e);
    
    void process(Event e);
    
    boolean isFinished();
}
