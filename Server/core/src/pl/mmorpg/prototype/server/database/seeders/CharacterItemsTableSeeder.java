package pl.mmorpg.prototype.server.database.seeders;

import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;

public class CharacterItemsTableSeeder extends TableSeeder
{

	@Override
	public Collection<Object> getRecords()
	{
		Collection<Object> records = new LinkedList<>();
		records.add(createCharacterItem("PankievChar", ItemIdentifiers.SMALL_HP_POTION, 5, new InventoryPosition(1, 1, 1)));
		records.add(createCharacterItem("PankievChar", ItemIdentifiers.SMALL_MP_POTION, 1, new InventoryPosition(1, 1, 2)));
		records.add(createCharacterItem("SmykChar", ItemIdentifiers.SMALL_MP_POTION, 7, new InventoryPosition(1, 1, 1)));
		return records;
	}

	private CharacterItem createCharacterItem(String characterName, ItemIdentifiers identifier, int count,
			InventoryPosition inventoryPosition)
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
		item.setIdentifier(identifier);
		item.setCount(count);
		item.setInventoryPosition(inventoryPosition);
		tx.commit();
		session.close();
		return item;
	}

}
