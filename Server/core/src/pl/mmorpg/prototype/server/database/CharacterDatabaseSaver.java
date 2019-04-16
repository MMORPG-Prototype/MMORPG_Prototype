package pl.mmorpg.prototype.server.database;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.*;
import pl.mmorpg.prototype.data.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.data.entities.repositories.*;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterDatabaseSaver
{
    private final CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
    private final CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);
    private final ItemQuickAccessBarConfigurationElementRepository itemQuickAccessBarConfigRepo = SpringContext
            .getBean(ItemQuickAccessBarConfigurationElementRepository.class);
    private final SpellQuickAccessBarConfigurationElementRepository spellQuickAccessBarConfigRepo = SpringContext
            .getBean(SpellQuickAccessBarConfigurationElementRepository.class);
    private final CharactersQuestsRepository charactersQuestsRepo = SpringContext
            .getBean(CharactersQuestsRepository.class);
    private final CharacterSpellRepository characterSpellRepo = SpringContext
            .getBean(CharacterSpellRepository.class);

    public void save(PlayerCharacter character)
    {
        saveCharacterItems(character);
        saveCharacterQuestStates(character);
        saveCharacterProperties(character);
        saveCharacterItemQuickAccessBarConfig(character);
        saveCharacterSpellQuickAccessBarConfig(character);
        saveCharacterSpells(character);
    }

	private void saveCharacterItems(PlayerCharacter character)
    {
        Collection<Item> items = character.getItems();
        Collection<CharacterItem> databaseItems = items.stream()
                .map((item) -> toDatabaseEquiv(item, (int) character.getId()))
                .collect(Collectors.toList());
        saveCharacterItems(character.getUserCharacterData(), databaseItems);
    }

    private void saveCharacterItems(Character toSave, Collection<CharacterItem> items)
    {
        List<CharacterItem> oldItems = itemRepo.findByCharacter(toSave);
        itemRepo.delete(oldItems);
        itemRepo.save(items);
    }

    private CharacterItem toDatabaseEquiv(Item item, int ownerId)
    {
        CharacterItem characterItem = new CharacterItem();
        Character character = characterRepo.findOne(ownerId);
        characterItem.setCharacter(character);
        characterItem.setIdentifier(item.getIdentifier());
        characterItem.setInventoryPosition(item.getInventoryPosition());
        if (item instanceof StackableItem)
            characterItem.setCount(((StackableItem) item).getCount());
        if (item instanceof EquipableItem)
        	characterItem.setEquipmentPosition(((EquipableItem) item).getEquipmentPosition());
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

    private void saveCharacterItemQuickAccessBarConfig(PlayerCharacter character)
    {
        Character userCharacterData = character.getUserCharacterData();
        itemQuickAccessBarConfigRepo.delete(itemQuickAccessBarConfigRepo.findByCharacter(userCharacterData));
        Collection<ItemQuickAccessBarConfigurationElement> configElements = userCharacterData.getItemQuickAccessBarConfig()
                .values();
        itemQuickAccessBarConfigRepo.save(configElements);
    }


	private void saveCharacterSpellQuickAccessBarConfig(PlayerCharacter character)
	{
		Character userCharacterData = character.getUserCharacterData();
        spellQuickAccessBarConfigRepo.delete(spellQuickAccessBarConfigRepo.findByCharacter(userCharacterData));
        Collection<SpellQuickAccessBarConfigurationElement> configElements = userCharacterData.getSpellQuickAccessBarConfig()
                .values();
        spellQuickAccessBarConfigRepo.save(configElements);
	}

    private void saveCharacterSpells(PlayerCharacter character)
    {
    	Collection<CharacterSpell> spells = character.getUserCharacterData().getSpells();
    	characterSpellRepo.save(spells);
    }
}
