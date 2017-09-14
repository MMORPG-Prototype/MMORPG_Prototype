package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;

import pl.mmorpg.prototype.server.exceptions.UserIsNotInGameException;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.quests.QuestTask;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

public class QuestTaskEventHandler implements EventHandler
{
    private GameDataRetriever gameDataRetriever;

    public QuestTaskEventHandler(GameDataRetriever gameDataRetriever)
    {
        this.gameDataRetriever = gameDataRetriever;
    }

    @Override
    public void handle(Event event, int connectionId, QuestFinishedObserver observer)
    {
        Collection<QuestTask> questTasks;
        try
        {
            questTasks = gameDataRetriever.getCharacterQuestTasksByConnectionId(connectionId);
        } catch (UserIsNotInGameException e)
        {
            return;
        }

        handle(questTasks, event, observer);
    }

    @Override
    public void handle(Collection<Event> events, int connectionId, QuestFinishedObserver observer)
    {
        if (!events.isEmpty())
        {
            Collection<QuestTask> questTasks;
            try
            {
                questTasks = gameDataRetriever.getCharacterQuestTasksByConnectionId(connectionId);
            } catch (UserIsNotInGameException e)
            {
                return;
            }

            handle(questTasks, events, observer);
        }
    }
}
