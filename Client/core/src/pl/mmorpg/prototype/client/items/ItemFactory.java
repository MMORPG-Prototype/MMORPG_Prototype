package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.UnknownItemIdentifierException;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class ItemFactory
{
    public static Item produceItem(CharacterItemDataPacket itemData)
    {
        String identifier = itemData.getIdentifier();
		if (identifier.equalsIgnoreCase(ItemIdentifiers.SMALL_HP_POTION.toString()))
            return new SmallHealthPotion(itemData.getId());
        else if (identifier.equalsIgnoreCase(ItemIdentifiers.SMALL_MP_POTION.toString()))
            return new SmallManaPotion(itemData.getId());

        throw new UnknownItemIdentifierException(identifier);
    }
}
