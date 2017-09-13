package pl.mmorpg.prototype.server.database.seeders;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;

public class UserCharactersQuestsTableSeeder implements TableSeeder
{
    private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);
    private final UserCharacterRepository characterRepository = SpringContext.getBean(UserCharacterRepository.class);

    @Override
    public void seed()
    {
        Iterable<Quest> allQuests = questRepository.findAll();
        Iterable<UserCharacter> allCharacters = characterRepository.findAll();

        for (UserCharacter character : allCharacters)
            for (Quest quest : allQuests)
                character.getQuests().add(new CharactersQuests(character, quest));
            

        characterRepository.save(allCharacters);
        questRepository.save(allQuests);
    }

}
