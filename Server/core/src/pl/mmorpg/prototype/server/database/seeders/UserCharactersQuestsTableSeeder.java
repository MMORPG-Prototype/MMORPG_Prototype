package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.CharactersQuestsRepository;
import pl.mmorpg.prototype.server.database.repositories.QuestRepository;
import pl.mmorpg.prototype.server.database.repositories.CharacterRepository;

public class UserCharactersQuestsTableSeeder implements TableSeeder
{
    private final QuestRepository questRepository = SpringContext.getBean(QuestRepository.class);
    private final CharacterRepository characterRepository = SpringContext.getBean(CharacterRepository.class);
    private final CharactersQuestsRepository charactersQuestRepository = SpringContext.getBean(CharactersQuestsRepository.class);

    @Override
    public void seed()
    {
        Iterable<Quest> allQuests = questRepository.findAll();
        Iterable<Character> allCharacters = characterRepository.findAll();

        Collection<CharactersQuests> joinEntites = new LinkedList<CharactersQuests>();
        for (Character character : allCharacters)
            for (Quest quest : allQuests)
            {
                CharactersQuests charactersQuests = new CharactersQuests(character, quest);
                joinEntites.add(charactersQuests);
            }
        
        charactersQuestRepository.save(joinEntites);
    }

}
