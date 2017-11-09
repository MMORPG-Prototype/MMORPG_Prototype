package pl.mmorpg.prototype.server.objects.monsters.abilities;

import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.Fireball;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class FireballAbility extends TimedAbility
{
	private final static float CAST_INTERVAL = 10000.0f;
	private Monster source;
	private GameObjectsContainer linkedContainer;

	public FireballAbility(Monster source, GameObjectsContainer linkedContainer)
	{
		super(CAST_INTERVAL);
		this.source = source;
		this.linkedContainer = linkedContainer;
	}


	@Override
	public void use(Monster target, PacketsSender packetSender)
	{
		Fireball fireball = new Fireball(IdSupplier.getId(), source, linkedContainer, packetSender);
        fireball.setTarget(target);
        fireball.setPosition(source.getX(), source.getY());
        linkedContainer.add(fireball);
        packetSender.sendToAll(PacketsMaker.makeCreationPacket(fireball));
	}


	@Override
	public boolean shouldBeUsedOnTargetedMonster()
	{
		return true;
	}


	@Override
	public boolean shouldBeUsedOnItself()
	{
		return false;
	}

}
