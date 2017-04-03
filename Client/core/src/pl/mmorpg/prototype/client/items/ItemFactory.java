package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.UnknownItemIdentifierException;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class ItemFactory
{
	public static Item produceItem(CharacterItemDataPacket itemData)
	{
		if (itemData.name.equalsIgnoreCase(ItemIdentifiers.SMALL_HEALTH_POTION))
			return new SmallHealthPotion();
		else if (itemData.name.equalsIgnoreCase(ItemIdentifiers.SMALL_MANA_POTION))
			return new SmallManaPotion();

		throw new UnknownItemIdentifierException(itemData.name);
	}
}
