package pl.mmorpg.prototype.server.database.repositories;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterSpell;
import pl.mmorpg.prototype.server.objects.monsters.spells.Spell;

public interface CharacterSpellRepository extends CrudRepository<CharacterSpell, Integer>
{
	@Cacheable("spell")
	CharacterSpell findByIdentifier(SpellIdentifiers identifier);
	
	Collection<Spell> findByCharacters(Character character);
}
