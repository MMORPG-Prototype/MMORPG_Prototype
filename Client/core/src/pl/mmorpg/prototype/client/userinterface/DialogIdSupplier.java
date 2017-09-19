package pl.mmorpg.prototype.client.userinterface;

class DialogIdSupplier
{
    private static long lastId = 0;
    
    long getId()
    {
        lastId++;
        return lastId;
    }
}
