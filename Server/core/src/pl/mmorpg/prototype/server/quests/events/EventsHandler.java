package pl.mmorpg.prototype.server.quests.events;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.mmorpg.prototype.clientservercommon.packets.quest.event.EventPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.data.entities.QuestTaskWrapper;
import pl.mmorpg.prototype.data.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.quests.QuestTask;
import pl.mmorpg.prototype.server.quests.observers.QuestFinishedObserver;

public class EventsHandler
{
    private final Queue<Event> eventQueue = new LinkedBlockingQueue<>();
    private final QuestFinishedObserver observer;
	private final PacketsSender packetsSender;

	public EventsHandler(QuestFinishedObserver observer, PacketsSender packetsSender)
    {
        this.observer = observer;
		this.packetsSender = packetsSender;
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
		Stream<Map.Entry<CharactersQuests, Collection<QuestTask>>> questTasks = getQuestTasks(receiver);
		questTasks.forEach(task -> task.getValue().forEach(t -> {
			boolean eventProcess = t.shouldProcess(event);
			EventPacket eventPacket = t.handleEvent(event, observer);
			if(eventProcess)
				packetsSender.sendTo(receiver.getConnectionId(), PacketsMaker.makeQuestStateInfoPacket(task.getKey()));
			if(eventPacket != null)
				packetsSender.sendTo(receiver.getConnectionId(), eventPacket);
		}));
	}

	private Stream<Map.Entry<CharactersQuests, Collection<QuestTask>>> getQuestTasks(PlayerCharacter playerCharacter)
    {
		return playerCharacter.getUserCharacterData().getQuests().stream()
				.map(this::toCharactersQuestsQuestsTasksEntry);
	}

	private Map.Entry<CharactersQuests, Collection<QuestTask>> toCharactersQuestsQuestsTasksEntry(
			CharactersQuests quest)
	{
		return new AbstractMap.SimpleEntry<>(quest,
				quest.getQuestTasks().stream()
						.map(QuestTaskWrapper::getQuestTask)
						.map(task -> (QuestTask) task)
						.collect(Collectors.toList())
		);
	}
}
