package pl.mmorpg.prototype.server.database.seeders;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.SpellIdentifiers;
import pl.mmorpg.prototype.data.entities.CharacterSpell;
import pl.mmorpg.prototype.data.entities.repositories.CharacterSpellRepository;

public class CharacterSpellsTableSeeder implements TableSeeder
{
	private final CharacterSpellRepository spellRepo = SpringContext.getBean(CharacterSpellRepository.class);

	@Override
	public void seed()
	{
		Collection<CharacterSpell> spells = Arrays.stream(SpellIdentifiers.values())
				.map(CharacterSpell::new)
				.collect(Collectors.toList());
		spellRepo.saveAll(spells);
	}

}
