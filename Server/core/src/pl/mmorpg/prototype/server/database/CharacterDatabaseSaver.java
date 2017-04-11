package pl.mmorpg.prototype.server.database;

import java.util.Collection;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.managers.CharacterItemTableManager;
import pl.mmorpg.prototype.server.database.managers.UserCharacterTableManager;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.items.StackableItem;

public class CharacterDatabaseSaver
{
	public static void save(PlayerCharacter character)
	{
		Collection<Item> items = character.getItems();
		Collection<CharacterItem> databaseItems = items.stream()
				.map((item) -> toDatabaseEquiv(item, (int)character.getId()))
				.collect(Collectors.toList());
		save((int)character.getId(), databaseItems);
	}
	
	private static CharacterItem toDatabaseEquiv(Item item, int ownerId)
	{
		CharacterItem characterItem = new CharacterItem();
		UserCharacter character = UserCharacterTableManager.getUserCharacter(ownerId);
		characterItem.setCharacter(character);
		characterItem.setIdentifier(item.getIdentifier());
		if(item instanceof StackableItem)
			characterItem.setCount(((StackableItem) item).getCount());
		return characterItem;
	}
	
    private static void save(Integer userCharacterId, Collection<CharacterItem> items)
    {
        UserCharacter userCharacter = UserCharacterTableManager.getUserCharacter(userCharacterId);
        save(userCharacter, items);
    }
    
    private static void save(UserCharacter toSave, Collection<CharacterItem> items)
    {
        CharacterItemTableManager.deleteAllCharacterItems(toSave);
        items.forEach((item) -> CharacterItemTableManager.save(item));
    }
}
