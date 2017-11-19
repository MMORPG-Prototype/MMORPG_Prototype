package pl.mmorpg.prototype.server.objects.monsters.spells.objects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.OffensiveSpell;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public abstract class ThrowableNonSplashAttackSpell extends ThrowableObject
{
    private PacketsSender packetsSender;
    private Monster source;
	private OffensiveSpell spell;

    public ThrowableNonSplashAttackSpell(OffensiveSpell spell, Texture lookout, long id, Monster source, GameObjectsContainer linkedContainer, PacketsSender packetsSender)
    {
        super(lookout, id, linkedContainer, packetsSender);
		this.spell = spell;
        this.source = source;
        this.packetsSender = packetsSender;
    }

	@Override
    public void onHit(Monster target)
    {
    	if(!target.isTargetingAnotherMonster())
    		target.targetMonster(source);
        MonsterProperties properties = target.getProperties();
        properties.hp -= spell.getDamage();
        informClientsAboutHit(target, packetsSender);
        if(properties.hp <= 0) 
        {
            source.killed(target);
            target.die();
        }
    }
    
    public abstract void informClientsAboutHit(Monster target, PacketsSender packetsSender);
}
