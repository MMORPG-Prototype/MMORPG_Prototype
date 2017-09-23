package pl.mmorpg.prototype.server.quests.events;

import java.util.Collection;

import pl.mmorpg.prototype.server.objects.PlayerCharacter;

public interface Event
{
    public Collection<PlayerCharacter> getReceivers();
    
    public void addReceiver(PlayerCharacter receiver);
}
