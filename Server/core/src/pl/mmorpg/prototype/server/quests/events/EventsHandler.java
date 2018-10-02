package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.mmorpg.prototype.server.database.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.quests.QuestTask;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

public class EventsHandler
{
    private final Queue<Event> eventQueue = new LinkedBlockingQueue<>();
    private final QuestFinishedObserver observer;
    
    public EventsHandler(QuestFinishedObserver observer)
    {
        this.observer = observer;
    }
    
    public void enqueueEvent(Event e)
    {
        eventQueue.add(e);
    }
    
    public void processEvents()
    {
        while(!eventQueue.isEmpty())
            processEvent(eventQueue.poll());
    }

    private void processEvent(Event event)
    {
        Collection<PlayerCharacter> receivers = event.getReceivers();
        receivers.forEach(r -> processEvent(r, event));
    }
    
    private void processEvent(PlayerCharacter receiver, Event event)
    {
        Stream<QuestTask> questTasks = getQuestTasks(receiver);
        questTasks.forEach(q -> q.handleEvent(event, observer));
    }

    private Stream<QuestTask> getQuestTasks(PlayerCharacter playerCharacter)
    {
        return playerCharacter.getUserCharacterData().getQuests().stream()
            .map(CharactersQuests::getQuestTasks)
            .flatMap(Collection::stream)
            .map(QuestTaskWrapper::getQuestTask);
    }
}
