package pl.mmorpg.prototype.server.database.seeders;

import com.google.common.collect.Lists;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.User;
import pl.mmorpg.prototype.data.entities.repositories.CharacterRepository;
import pl.mmorpg.prototype.data.entities.repositories.CharacterSpellRepository;
import pl.mmorpg.prototype.data.entities.repositories.UserRepository;

public class CharactersTableSeeder implements TableSeeder
{
	private final CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
	private final UserRepository userRepo = SpringContext.getBean(UserRepository.class);
	private final CharacterSpellRepository spellRepo = SpringContext.getBean(CharacterSpellRepository.class);

	@Override
	public void seed()
	{
		characterRepo.save(createUserCharacter("Pankiev", "PankievChar"));
		characterRepo.save(createUserCharacter("Smyk", "SmykChar"));
	}

	private Character createUserCharacter(String username, String characterName)
	{
		User user = userRepo.findByUsername(username).get();
		Character character = new Character();
		character.setUser(user);
		character.setNickname(characterName);
		character.setSpells(Lists.newArrayList(spellRepo.findAll()));
		return character;
	}

}
