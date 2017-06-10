package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonstersFactory extends ObjectsFactory
{

    public Monster produce(MonsterCreationPacket packet)
    {
        Monster monster = (Monster) super.produce(packet);
        monster.setProperties(packet.properties);
        return monster;
    }

} 
