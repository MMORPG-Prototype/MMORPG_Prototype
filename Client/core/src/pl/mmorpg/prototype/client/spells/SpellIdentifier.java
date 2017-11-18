package pl.mmorpg.prototype.client.spells;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.client.exceptions.UnknownSpellException;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

public class SpellIdentifier
{
	private static final Map<Class<?>, SpellIdentifiers> identifiers = new HashMap<>();

	static
	{
		identifiers.put(FireballSpell.class, SpellIdentifiers.FIREBALL);
	}

	public static SpellIdentifiers getSpellIdentifier(Class<?> type)
	{
		SpellIdentifiers identifier = identifiers.get(type);
		if(identifier == null)
			throw new UnknownSpellException(type);
		
		return identifier;
	}
}
