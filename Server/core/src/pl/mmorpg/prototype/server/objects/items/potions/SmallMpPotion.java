package pl.mmorpg.prototype.server.objects.items.potions;



import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class SmallMpPotion extends Potion
{
    private static final int MANA_REPLENISH_POWER = 15;
    
    public SmallMpPotion(long id, int count)
    {
        super(id, count);
    }

    public SmallMpPotion(long id)
    {
        super(id);
    }

    @Override
    public void useItem(Monster target, PacketsSender packetSender)
    {
        MonsterProperties targetProperties = target.getProperties();
        Statistics targetStatistics = target.getStatistics();
        int previous = targetProperties.mp;
        targetProperties.mp += MANA_REPLENISH_POWER;
        if(targetProperties.mp > targetStatistics.maxMp)
            targetProperties.mp = targetStatistics.maxMp;
        int delta = targetProperties.mp - previous;
        packetSender.sendToAll(PacketsMaker.makeMpChangeByItemUsagePacket(delta, target.getId()));

    }

}
