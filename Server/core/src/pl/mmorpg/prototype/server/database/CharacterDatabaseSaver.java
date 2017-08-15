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
		saveCharacterItems(character);
		saveCharacterProperties(character);
	}

	private static void saveCharacterItems(PlayerCharacter character)
	{
		Collection<Item> items = character.getItems();
		Collection<CharacterItem> databaseItems = items.stream()
				.map((item) -> toDatabaseEquiv(item, (int)character.getId()))
				.collect(Collectors.toList());
		saveCharacterItems(character.getUserCharacterData(), databaseItems);
	}
	
	private static CharacterItem toDatabaseEquiv(Item item, int ownerId)
	{
		CharacterItem characterItem = new CharacterItem();
		UserCharacter character = UserCharacterTableManager.getUserCharacter(ownerId);
		characterItem.setCharacter(character);
		characterItem.setIdentifier(item.getIdentifier());
		characterItem.setInventoryPosition(item.getInventoryPosition());
		if(item instanceof StackableItem)
			characterItem.setCount(((StackableItem) item).getCount());
		return characterItem;
	}
	    
    private static void saveCharacterItems(UserCharacter toSave, Collection<CharacterItem> items)
    {
        CharacterItemTableManager.deleteAllCharacterItems(toSave);
        items.forEach((item) -> CharacterItemTableManager.save(item));
    }
    
	private static void saveCharacterProperties(PlayerCharacter character)
	{
		character.updateUserCharacterData();
		UserCharacter userCharacterData = character.getUserCharacterData();
		HibernateUtil.makeOperation( session -> session.update(userCharacterData));
	}
}
