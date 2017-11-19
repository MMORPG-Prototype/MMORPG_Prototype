package pl.mmorpg.prototype.server.objects.monsters.spells;

import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class HealSpell implements Spell
{
	private static final int healPower = 20;
	
	@Override
	public void onUse(PlayerCharacter source, Monster target, PacketsSender packetsSender,
			GameObjectsContainer gameObjectsContainer)
	{
		source.spellUsed(this);
		target.heal(healPower);
	}
	
	@Override
	public int getNeededMana()
	{
		return 10;
	}

	@Override
	public SpellIdentifiers getSpellType()
	{
		return SpellIdentifiers.HEAL;
	}

}
