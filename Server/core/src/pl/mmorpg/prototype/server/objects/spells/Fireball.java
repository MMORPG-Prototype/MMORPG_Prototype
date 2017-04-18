package pl.mmorpg.prototype.server.objects.spells;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.ItemsOwner;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class Fireball extends Spell
{
    public static final int DAMAGE = 20;
    public static final int MANA_DRAIN = 10;
    private PacketsSender packetSender;
    private ItemsOwner source;

    public Fireball(long id, Monster source, GameObjectsContainer linkedContainer, PacketsSender packetSender)
    {
        super(Assets.get("fireball.png"), id, source, linkedContainer, packetSender);
        this.source = source;
        this.packetSender = packetSender;
    }

    @Override
    public int getSpellDamage()
    {
        return 20;
    }
}
