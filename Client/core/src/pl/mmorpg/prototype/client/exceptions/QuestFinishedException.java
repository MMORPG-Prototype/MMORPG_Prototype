package pl.mmorpg.prototype.client.exceptions;

public class QuestFinishedException extends GameException
{
    public QuestFinishedException()
    {
        super("Cannot retrieve current task, because the quest is already finished");
    }
}
