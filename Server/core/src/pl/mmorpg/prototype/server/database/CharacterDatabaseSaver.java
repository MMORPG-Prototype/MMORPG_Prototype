package pl.mmorpg.prototype.server.database;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.QuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.UserCharacterSpell;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.server.database.repositories.CharactersQuestsRepository;
import pl.mmorpg.prototype.server.database.repositories.QuickAccessBarConfigurationElementRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterSpellRepository;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;

public class CharacterDatabaseSaver
{
    private final UserCharacterRepository characterRepo = SpringContext.getBean(UserCharacterRepository.class);
    private final CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);
    private final QuickAccessBarConfigurationElementRepository quickAccessBarConfigRepo = SpringContext
            .getBean(QuickAccessBarConfigurationElementRepository.class);
    private final CharactersQuestsRepository charactersQuestsRepo = SpringContext
            .getBean(CharactersQuestsRepository.class);
    private final UserCharacterSpellRepository characterSpellRepo = SpringContext
            .getBean(UserCharacterSpellRepository.class);

    public void save(PlayerCharacter character)
    {
        saveCharacterItems(character);
        saveCharacterQuestStates(character);
        saveCharacterProperties(character);
        saveCharacterQuickAccessBarConfig(character);
        saveCharacterSpells(character);
    }

	private void saveCharacterItems(PlayerCharacter character)
    {
        Collection<Item> items = character.getItems();
        Collection<CharacterItem> databaseItems = items.stream()
                .map((item) -> toDatabaseEquiv(item, (int) character.getId())).collect(Collectors.toList());
        saveCharacterItems(character.getUserCharacterData(), databaseItems);
    }

    private void saveCharacterItems(UserCharacter toSave, Collection<CharacterItem> items)
    {
        List<CharacterItem> oldItems = itemRepo.findByCharacter(toSave);
        itemRepo.delete(oldItems);
        itemRepo.save(items);
    }

    private CharacterItem toDatabaseEquiv(Item item, int ownerId)
    {
        CharacterItem characterItem = new CharacterItem();
        UserCharacter character = characterRepo.findOne(ownerId);
        characterItem.setCharacter(character);
        characterItem.setIdentifier(item.getIdentifier());
        characterItem.setInventoryPosition(item.getInventoryPosition());
        if (item instanceof StackableItem)
            characterItem.setCount(((StackableItem) item).getCount());
        return characterItem;
    }

    private void saveCharacterProperties(PlayerCharacter character)
    {
    	character.updateUserCharacterProperties();
        characterRepo.save(character.getUserCharacterData());
    }

    private void saveCharacterQuestStates(PlayerCharacter character)
    {
        Collection<CharactersQuests> quests = character.getUserCharacterData().getQuests();
        charactersQuestsRepo.save(quests);
    }

    private void saveCharacterQuickAccessBarConfig(PlayerCharacter character)
    {
        UserCharacter userCharacterData = character.getUserCharacterData();
        quickAccessBarConfigRepo.delete(quickAccessBarConfigRepo.findByCharacter(userCharacterData));
        Collection<QuickAccessBarConfigurationElement> configElements = userCharacterData.getQuickAccessBarConfig()
                .values();
        quickAccessBarConfigRepo.save(configElements);
    }
    
    private void saveCharacterSpells(PlayerCharacter character)
    {
    	Collection<UserCharacterSpell> spells = character.getUserCharacterData().getSpells();
    	characterSpellRepo.save(spells);
    }
}
