package pl.mmorpg.prototype.server.database;

import java.util.List;

import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.managers.CharacterItemTableManager;
import pl.mmorpg.prototype.server.database.managers.UserCharacterTableManager;

public class CharacterSaver
{
    public void save(Integer userCharacterId, List<CharacterItem> items)
    {
        UserCharacter userCharacter = UserCharacterTableManager.getUserCharacter(userCharacterId);
        save(userCharacter, items);
    }
    
    public void save(UserCharacter toSave, List<CharacterItem> items)
    {
        CharacterItemTableManager.deleteAllCharacterItems(toSave);
        items.forEach((item) -> CharacterItemTableManager.save(item));
    }
}
