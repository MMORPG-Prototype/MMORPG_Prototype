package pl.mmorpg.prototype.client.quests;

import lombok.Data;

@Data
public class QuestTask
{
    private final String description;
    private final float percentFinished;
    
    public boolean isFinished()
    {
        return percentFinished >= 100.0f;
    }
}
