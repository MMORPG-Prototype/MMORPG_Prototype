package pl.mmorpg.prototype.client.quests;

import lombok.Data;
import pl.mmorpg.prototype.clientservercommon.packets.QuestTaskInfoPacket;

import java.util.Arrays;

@Data
public class QuestTask
{
    private final String description;
    private final float percentFinished;
    private final QuestTask[] nextTasks;

    public QuestTask(QuestTaskInfoPacket questTask)
    {
        this.description = questTask.getDescription();
        this.percentFinished = questTask.getPercentFinished();
        this.nextTasks = Arrays.stream(questTask.getNextTasks())
                .map(QuestTask::new)
                .toArray(QuestTask[]::new);
    }

    public boolean isFinished()
    {
        return percentFinished >= 100.0f;
    }
}
