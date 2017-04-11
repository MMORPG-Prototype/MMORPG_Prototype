package pl.mmorpg.prototype.server.objects.items;



import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
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
        MonsterProperties targetProperties = target.getProperites();
        int previous = targetProperties.mp;
        targetProperties.mp += MANA_REPLENISH_POWER; 
        if(targetProperties.mp > targetProperties.maxMp)
            targetProperties.mp = targetProperties.maxMp;
        int delta = targetProperties.mp - previous;
        packetSender.send(PacketsMaker.makeMpChangeByItemUsagePacket(delta, target.getId()));

    }

}
