package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;
import java.util.List;

import pl.mmorpg.prototype.server.exceptions.UserIsNotInGameException;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.quests.QuestTask;

public class QuestTaskEventHandler implements EventHandler
{
    private GameDataRetriever gameDataRetriever;

    public QuestTaskEventHandler(GameDataRetriever gameDataRetriever)
    {
        this.gameDataRetriever = gameDataRetriever;
    }

    @Override
    public void handle(Event event, int connectionId)
    {
        List<QuestTask> questTasks;
        try
        {
            questTasks = gameDataRetriever.getCharacterQuestTasksByConnectionId(connectionId);
        } catch (UserIsNotInGameException e)
        {
            return;
        }

        handle(questTasks, event);
    }

    @Override
    public void handle(Collection<Event> events, int connectionId)
    {
        if (!events.isEmpty())
        {
            List<QuestTask> questTasks;
            try
            {
                questTasks = gameDataRetriever.getCharacterQuestTasksByConnectionId(connectionId);
            } catch (UserIsNotInGameException e)
            {
                return;
            }

            handle(questTasks, events);
        }
    }

    private void handle(List<QuestTask> questTasks, Collection<Event> events)
    {
        events.forEach(e -> handle(questTasks, e));
    }

    private void handle(List<QuestTask> questTasks, Event e)
    {
        questTasks.forEach(task -> handle(task, e));
    }

    private void handle(QuestTask task, Event e)
    {
        if (task.shouldProcess(e))
            task.process(e);
    }

}
