package pl.mmorpg.prototype.client.quests;

import lombok.Data;

@Data
public class ProgressStep
{
    private final String description;
    private boolean isFinished;
}
