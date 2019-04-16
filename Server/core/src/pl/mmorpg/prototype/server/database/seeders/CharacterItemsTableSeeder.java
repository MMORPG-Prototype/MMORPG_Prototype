package pl.mmorpg.prototype.server.database.seeders;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.data.entities.CharacterItem;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;
import pl.mmorpg.prototype.data.entities.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.data.entities.repositories.CharacterRepository;

public class CharacterItemsTableSeeder implements TableSeeder
{
	private final CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);
	private final CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);

	@Override
	public void seed()
	{
		itemRepo.save(createCharacterItem("PankievChar", ItemIdentifiers.SMALL_HP_POTION, 5, new InventoryPosition(2, 1, 1)));
		itemRepo.save(createCharacterItem("PankievChar", ItemIdentifiers.SMALL_MP_POTION, 1, new InventoryPosition(1, 1, 1)));
		itemRepo.save(createCharacterItem("PankievChar", ItemIdentifiers.SWORD, 1, new InventoryPosition(0, 1, 0)));
		itemRepo.save(createCharacterItem("SmykChar", ItemIdentifiers.SMALL_MP_POTION, 7, new InventoryPosition(1, 1, 1)));
	}

	private CharacterItem createCharacterItem(String characterName, ItemIdentifiers identifier, int count,
			InventoryPosition inventoryPosition)
	{
		Character character = characterRepo.findByNickname(characterName);	
		CharacterItem item = new CharacterItem();
		item.setCharacter(character);
		item.setIdentifier(identifier);
		item.setCount(count);
		item.setInventoryPosition(inventoryPosition);
		itemRepo.save(item);
		return item;
	}

}
