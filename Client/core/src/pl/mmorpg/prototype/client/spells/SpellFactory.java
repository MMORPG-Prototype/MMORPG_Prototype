package pl.mmorpg.prototype.client.spells;

import pl.mmorpg.prototype.client.exceptions.UnknownSpellException;
import pl.mmorpg.prototype.client.objects.icons.spells.Spell;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

public class SpellFactory
{
	public static Spell produce(SpellIdentifiers identifier)
	{
		if(identifier.equals(SpellIdentifier.getSpellIdentifier(FireballSpell.class)))
			return new FireballSpell();
		
		throw new UnknownSpellException(identifier.toString());
	}
}
