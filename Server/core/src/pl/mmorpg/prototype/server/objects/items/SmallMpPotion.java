package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
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
    public void useItem(Monster target)
    {
        MonsterProperties targetProperties = target.getProperites();
        targetProperties.mp += MANA_REPLENISH_POWER;
        if(targetProperties.mp > targetProperties.maxMp)
            targetProperties.mp = targetProperties.maxMp;

    }

}
