package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;

public class UnknownSpellException extends GameException
{
	public UnknownSpellException(SpellIdentifiers spellType)
	{
		super(spellType.toString());
	}
}
