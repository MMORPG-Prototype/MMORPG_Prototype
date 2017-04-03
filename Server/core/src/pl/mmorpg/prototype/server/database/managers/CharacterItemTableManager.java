package pl.mmorpg.prototype.server.database.managers;

import java.util.List;

import pl.mmorpg.prototype.server.database.entities.CharacterItem;

public class CharacterItemTableManager
{
	public static List<CharacterItem> getCharacterItems(int characterId)
	{
		List<CharacterItem> items = TableManagerHelper.getListOfDataElements("CharacterItem",
				"CharacterItem.character.id = " + characterId, CharacterItem.class);
		return items;
	}
}
