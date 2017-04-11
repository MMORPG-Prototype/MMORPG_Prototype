package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.clientservercommon.ItemTypes;
import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class CharacterItemsTableSeeder extends TableSeeder
{

    @Override
    public Collection<Object> getRecords()
    {
        Collection<Object> records = new LinkedList<>();
        records.add(createCharacterItem("PankievChar", ItemTypes.SMALL_HP_POTION, "Small Health potion"));
        records.add(createCharacterItem("PankievChar", ItemTypes.SMALL_MP_POTION, "Small mana potion"));
        records.add(createCharacterItem("SmykChar", ItemTypes.SMALL_MP_POTION, "Small mana potion"));
        return records;
    }

    private CharacterItem createCharacterItem(String characterName, ItemTypes type, String name)
    {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        Query<UserCharacter> getChar = session
                .createQuery("from " + UserCharacter.class.getName() + " as char where char.nickname=:nickname",
                        UserCharacter.class)
                .setParameter("nickname", characterName);
        UserCharacter character = getChar.getSingleResult();
        CharacterItem item = new CharacterItem();
        item.setCharacter(character);
        item.setType(type);
        item.setName(name);
        tx.commit();
        session.close();
        return item;
    }

}
