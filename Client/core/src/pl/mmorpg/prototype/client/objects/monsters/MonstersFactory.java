package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;

public class MonstersFactory extends ObjectsFactory
{

    public Monster produce(MonsterCreationPacket packet, CollisionMap<GameObject> linkedCollisionMap)
    {
        Monster monster = (Monster) super.produce(packet, linkedCollisionMap);
        monster.setProperties(packet.properties);
        return monster;
    }

} 
