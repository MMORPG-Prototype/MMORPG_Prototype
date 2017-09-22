package pl.mmorpg.prototype.client.quests;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;

public class QuestCreator
{

    public static Quest create(QuestStateInfoPacket packet)
    {
        Collection<QuestTask> questTasks = Arrays.stream(packet.getQuestTasks())
                .map(questTask -> new QuestTask(questTask.getDescription(), questTask.getPercentFinished()))
                .collect(Collectors.toList());
        Progress progress = new Progress(questTasks);
        Quest quest = new Quest(progress, packet.getQuestName(), packet.getDescription());
        return quest;
    }
}
