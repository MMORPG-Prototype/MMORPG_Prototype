package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.PlayerCharacter;

public interface Event
{
    Collection<PlayerCharacter> getReceivers();
    
    void addReceiver(PlayerCharacter receiver);
}
