package pl.mmorpg.prototype.server.exceptions;

public class QuestMustHaveExaclyOneRootTaskException extends GameException
{
    
    public QuestMustHaveExaclyOneRootTaskException(int size)
    {
        super("Actual size" + size);
    }

}
