package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.quests.QuestTask;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

public interface EventHandler
{
    void handle(Event e, int connectionId, QuestFinishedObserver observer);
    
    void handle(Collection<Event> events, int connectionId, QuestFinishedObserver observer);
    
    default void handle(Iterable<QuestTask> questTasks, Collection<Event> events, QuestFinishedObserver observer)
    {
        events.forEach(e -> handle(questTasks, e, observer));
    }

    default void handle(Iterable<QuestTask> questTasks, Event e, QuestFinishedObserver observer)
    {
        questTasks.forEach(task -> task.handleEvent(e, observer));
    }

    default void handle(PlayerCharacter playerCharacter, Event e, QuestFinishedObserver observer)
    {
        List<QuestTask> questTasks = getQuestTasks(playerCharacter);      
        handle(questTasks, e, observer);    
    }
    
    default void handle(PlayerCharacter playerCharacter, Collection<Event> events, QuestFinishedObserver observer)
    {
        List<QuestTask> questTasks = getQuestTasks(playerCharacter);
        handle(questTasks, events, observer);    
    }

    default List<QuestTask> getQuestTasks(PlayerCharacter playerCharacter)
    {
        return playerCharacter.getUserCharacterData().getQuests().stream()
            .map(CharactersQuests::getQuestTasks)
            .flatMap(Collection::stream)
            .map(QuestTaskWrapper::getQuestTask)
            .collect(Collectors.toList());
    }
    
    
}
