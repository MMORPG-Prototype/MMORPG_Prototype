package pl.mmorpg.prototype.client.quests;

import lombok.Data;

@Data
public class QuestTask
{
    private final String description;
    private float percentFinished;
    
    public QuestTask(String description, float percentFinished)
    {
        this.description = description;
        this.percentFinished = percentFinished;
    }
    
    public boolean isFinished()
    {
        return percentFinished >= 100.0f;
    }
}
