package pl.mmorpg.prototype.server.objects.monsters.spells;

import pl.mmorpg.prototype.server.database.entities.UserCharacterSpell;
import pl.mmorpg.prototype.server.exceptions.UnknownSpellException;

public class SpellFactory
{
	public static Spell create(UserCharacterSpell spellModel)
	{
		SpellTypes spellType = spellModel.getSpellType();
		
		if (spellType.equals(SpellTypes.FIREBALL))
			return new FireballSpell();
		throw new UnknownSpellException(spellType);
	}
}
