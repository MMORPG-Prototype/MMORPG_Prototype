package pl.mmorpg.prototype.server.database.seeders;

import pl.mmorpg.prototype.SpringApplicationContext;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.database.repositories.UserRepository;

public class UserCharactersTableSeeder implements TableSeeder
{
	private final UserCharacterRepository characterRepo = SpringApplicationContext.getBean(UserCharacterRepository.class);
	private final UserRepository userRepo = SpringApplicationContext.getBean(UserRepository.class);
	
	@Override
	public void seed()
	{
		characterRepo.save(createUserCharacter("Pankiev", "PankievChar"));
		characterRepo.save(createUserCharacter("Smyk", "SmykChar"));
	}

	private UserCharacter createUserCharacter(String username, String characterName)
	{
		User user = userRepo.findByUsername(username);
		UserCharacter character = new UserCharacter();
		character.setUser(user);
		character.setNickname(characterName);
		return character;
	}

}
