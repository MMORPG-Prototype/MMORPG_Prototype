package pl.mmorpg.prototype.server.exceptions;

import pl.mmorpg.prototype.server.objects.monsters.spells.SpellTypes;

public class UnknownSpellException extends GameException
{

	public UnknownSpellException(SpellTypes spellType)
	{
		super(spellType.toString());
	}

}
