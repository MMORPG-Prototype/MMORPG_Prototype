package pl.mmorpg.prototype.server.objects.items;


import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class SmallHpPotion extends Potion
{
    private static final int HEAL_POWER = 15;
    
    public SmallHpPotion(long id, int count)
    {
        super(id, count);
    }

    public SmallHpPotion(long id)
    {
        super(id);
    }

    @Override
    public void useItem(Monster target, PacketsSender packetSender)
    {
        MonsterProperties targetProperties = target.getProperties();
        int previous = targetProperties.hp;
        targetProperties.hp += HEAL_POWER;
        if(targetProperties.hp > targetProperties.maxHp)
            targetProperties.hp = targetProperties.maxHp;
        int delta = targetProperties.hp - previous;
        packetSender.sendToAll(PacketsMaker.makeHpNotifiedIncreasePacket(delta, target.getId()));
    }

}
