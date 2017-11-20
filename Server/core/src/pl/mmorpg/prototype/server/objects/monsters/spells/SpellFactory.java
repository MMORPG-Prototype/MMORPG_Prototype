package pl.mmorpg.prototype.server.objects.monsters.spells;

import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterSpell;
import pl.mmorpg.prototype.server.exceptions.UnknownSpellException;

public class SpellFactory
{
	public static Spell create(CharacterSpell spellModel)
	{
		SpellIdentifiers spellType = spellModel.getIdentifier();
		
		if (spellType.equals(SpellIdentifiers.FIREBALL))
			return new FireballSpell();
		if (spellType.equals(SpellIdentifiers.HEAL))
			return new HealSpell();
		throw new UnknownSpellException(spellType);
	}
}
