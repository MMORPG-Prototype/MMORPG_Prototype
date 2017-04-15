package pl.mmorpg.prototype.server.objects.spells;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class Fireball extends ThrowableObject
{
    private PacketsSender packetSender;
    private static final int spellDamage = 20;

    public Fireball(Texture lookout, long id, CollisionMap<GameObject> collisionMap,
            GameObjectsContainer linkedContainer, PacketsSender packetSender)
    {
        super(lookout, id, collisionMap, linkedContainer, packetSender);
        this.packetSender = packetSender;
    }

    @Override
    public void onFinish(Monster target)
    {
        target.getProperites().hp -= spellDamage;
        packetSender.send(PacketsMaker.makeFireDamagePacket(getId(), spellDamage));
    }

}
