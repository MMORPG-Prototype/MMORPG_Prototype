package pl.mmorpg.prototype.server.database;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.SpringApplicationContext;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;

public class CharacterDatabaseSaver
{
	private final UserCharacterRepository characterRepo = SpringApplicationContext
			.getBean(UserCharacterRepository.class);
	private final CharacterItemRepository itemRepo = SpringApplicationContext.getBean(CharacterItemRepository.class);

	public void save(PlayerCharacter character)
	{
		saveCharacterItems(character);
		saveCharacterProperties(character);
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
		character.updateUserCharacterData();
		characterRepo.save(character.getUserCharacterData());
	}
}
