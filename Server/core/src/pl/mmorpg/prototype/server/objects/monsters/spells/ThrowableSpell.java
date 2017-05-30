package pl.mmorpg.prototype.server.objects.monsters.spells;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public abstract class ThrowableSpell extends ThrowableObject
{
    private PacketsSender packetSender;
    private Monster source;

    public ThrowableSpell(Texture lookout, long id, Monster source, GameObjectsContainer linkedContainer, PacketsSender packetSender)
    {
        super(lookout, id, linkedContainer, packetSender);
        this.source = source;
        this.packetSender = packetSender;
    }

    @Override
    public void onFinish(Monster target)
    {
    	if(!target.isTargetingAnotherMonster())
    		target.targetMonster(source);
        int spellDamage = getSpellDamage();
        MonsterProperties properties = target.getProperties();
        properties.hp -= spellDamage;
        packetSender.sendToAll(PacketsMaker.makeFireDamagePacket(target.getId(), spellDamage));
        if(properties.hp <= 0) 
        {
            source.killed(target);
            target.die();
        }
    }
    
    public abstract int getSpellDamage();

}
