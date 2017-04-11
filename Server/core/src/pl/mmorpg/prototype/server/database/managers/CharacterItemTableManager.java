package pl.mmorpg.prototype.server.database.managers;

import java.util.List;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class CharacterItemTableManager
{
    public static List<CharacterItem> getCharacterItems(int characterId)
    {
        List<CharacterItem> items = TableManagerHelper.getListOfDataElements("CharacterItem",
                "CharacterItem.character.id = " + characterId, CharacterItem.class);
        return items;
    }
    
    public static void deleteAllCharacterItems(UserCharacter character)
    {
        HibernateUtil.makeOperation((session) -> 
        {
          session.createQuery("delete from CharacterItem item where item.character.id = :characterId")
          .setParameter("characterId", character.getId())
          .executeUpdate();
        });
    }

    public static void save(CharacterItem item)
    {
        HibernateUtil.makeOperation((session) -> session.save(item));
    }
    
}
