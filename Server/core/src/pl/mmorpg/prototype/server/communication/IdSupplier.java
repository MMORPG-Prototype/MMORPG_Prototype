package pl.mmorpg.prototype.server.communication;

public class IdSupplier
{
	private static long lastId = Integer.MAX_VALUE;
    
    public static long getId()
    {
        lastId++;
        if(lastId == 0)
            lastId = ((long)Integer.MAX_VALUE) + 1;
        return lastId;
    }
}
