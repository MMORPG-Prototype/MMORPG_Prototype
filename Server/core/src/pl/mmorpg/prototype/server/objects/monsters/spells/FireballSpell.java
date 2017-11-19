package pl.mmorpg.prototype.server.objects.monsters.spells;

import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.objects.Fireball;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class FireballSpell implements OffensiveSpell
{
	@Override
	public int getDamage()
	{
		return 20;
	}

	@Override
	public int getNeededMana()
	{
		return 10;
	}

	@Override
	public void onUse(PlayerCharacter character, Monster target, PacketsSender packetsSender,
			GameObjectsContainer gameObjectsContainer)
	{		
		Fireball fireball = new Fireball(character.getKnownSpell(FireballSpell.class), IdSupplier.getId(), character, gameObjectsContainer, packetsSender);
		fireball.setTarget(target);
		fireball.setPosition(character.getX(), character.getY());
		gameObjectsContainer.add(fireball);
		packetsSender.sendToAll(PacketsMaker.makeCreationPacket(fireball));
	    packetsSender.sendTo(character.getConnectionId(), PacketsMaker.makeManaDrainPacket(getNeededMana()));
	    character.spellUsed(this);
	}

	@Override
	public SpellIdentifiers getSpellType()
	{
		return SpellIdentifiers.FIREBALL;
	}


}
