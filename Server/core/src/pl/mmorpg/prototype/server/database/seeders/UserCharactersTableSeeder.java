package pl.mmorpg.prototype.server.database.seeders;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.repositories.CharacterRepository;
import pl.mmorpg.prototype.server.database.repositories.UserRepository;

public class UserCharactersTableSeeder implements TableSeeder
{
	private final CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
	private final UserRepository userRepo = SpringContext.getBean(UserRepository.class);
	
	@Override
	public void seed()
	{
		characterRepo.save(createUserCharacter("Pankiev", "PankievChar"));
		characterRepo.save(createUserCharacter("Smyk", "SmykChar"));
	}

	private Character createUserCharacter(String username, String characterName)
	{
		User user = userRepo.findByUsername(username);
		Character character = new Character();
		character.setUser(user);
		character.setNickname(characterName);
		return character;
	}

}
