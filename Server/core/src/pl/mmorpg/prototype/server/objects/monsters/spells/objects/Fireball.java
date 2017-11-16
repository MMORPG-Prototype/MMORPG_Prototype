package pl.mmorpg.prototype.server.objects.monsters.spells.objects;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.FireballSpell;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class Fireball extends ThrowableNonSplashAttackSpell
{
    private FireballSpell fireballSpell;

	public Fireball(FireballSpell fireballSpell, long id, Monster source, GameObjectsContainer linkedContainer, PacketsSender packetSender)
    {
        super(fireballSpell, Assets.get("fireball.png"), id, source, linkedContainer, packetSender);
		this.fireballSpell = fireballSpell;
    }

	@Override
	public void informClientsAboutHit(Monster target, PacketsSender packetsSender)
	{
        packetsSender.sendToAll(PacketsMaker.makeFireDamagePacket(target.getId(), fireballSpell.getDamage()));
	}
}
