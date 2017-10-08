package pl.mmorpg.prototype.server.quests.events;

import java.util.ArrayList;
import java.util.List;

import pl.mmorpg.prototype.server.objects.PlayerCharacter;

public abstract class EventBase implements Event
{
    private final List<PlayerCharacter> receivers = new ArrayList<>(1);
    
    @Override
    public List<PlayerCharacter> getReceivers()
    {
        return receivers;
    }
    
    @Override
    public void addReceiver(PlayerCharacter receiver)
    {
        receivers.add(receiver);
    }
    
}
