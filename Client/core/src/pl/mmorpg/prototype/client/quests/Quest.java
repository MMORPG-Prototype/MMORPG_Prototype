package pl.mmorpg.prototype.client.quests;

import lombok.Data;

@Data
public class Quest
{
    private final Progress progress;
    private final String questName;
    private final String description;
    private final int[] finishedQuestTasksPath;
    private final QuestTask rootTask;
}
