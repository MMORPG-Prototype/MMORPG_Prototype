package pl.mmorpg.prototype.server.quests.events;

import pl.mmorpg.prototype.server.objects.PlayerCharacter;

import java.util.Collection;

public interface Event
{
    Collection<PlayerCharacter> getReceivers();
    
    void addReceiver(PlayerCharacter receiver);
}
