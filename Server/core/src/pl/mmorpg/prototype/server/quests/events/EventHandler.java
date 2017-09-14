package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;

public interface EventHandler
{
    void handle(Event e, int connectionId);
    
    void handle(Collection<Event> events, int connectionId);
}
