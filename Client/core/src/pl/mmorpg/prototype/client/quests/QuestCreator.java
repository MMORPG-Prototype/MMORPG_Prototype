package pl.mmorpg.prototype.client.quests;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;

public class QuestCreator
{

	public static Quest create(QuestStateInfoPacket packet)
	{
		QuestTask rootTask = new QuestTask(packet.getRootTask());
		int[] finishedQuestTasksPath = Stream.of(packet.getFinishedQuestTasksPath().split(","))
				.mapToInt(Integer::valueOf)
				.toArray();
		Progress progress = new Progress(rootTask, finishedQuestTasksPath);
		return new Quest(progress, packet.getQuestName(), packet.getDescription(),
				finishedQuestTasksPath, rootTask);
	}
}
