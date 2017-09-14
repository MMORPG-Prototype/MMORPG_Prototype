package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.CharactersQuestsRepository;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;

public class UserCharactersQuestsTableSeeder implements TableSeeder
{
    private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);
    private final UserCharacterRepository characterRepository = SpringContext.getBean(UserCharacterRepository.class);
    private final CharactersQuestsRepository charactersQuestRepository = SpringContext.getBean(CharactersQuestsRepository.class);

    @Override
    public void seed()
    {
        Iterable<Quest> allQuests = questRepository.findAll();
        Iterable<UserCharacter> allCharacters = characterRepository.findAll();

        Collection<CharactersQuests> joinEntites = new LinkedList<CharactersQuests>();
        for (UserCharacter character : allCharacters)
            for (Quest quest : allQuests)
            {
                CharactersQuests charactersQuests = new CharactersQuests(character, quest);
                joinEntites.add(charactersQuests);
            }
        
        charactersQuestRepository.save(joinEntites);
    }

}
