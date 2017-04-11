package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.exceptions.UnknownItemIdentifierException;
import pl.mmorpg.prototype.clientservercommon.ItemTypes;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;

public class ItemFactory
{
    public static Item produceItem(CharacterItemDataPacket itemData)
    {
        if (itemData.getType().equalsIgnoreCase(ItemTypes.SMALL_HP_POTION.toString()))
            return new SmallHealthPotion(itemData.getId());
        else if (itemData.getType().equalsIgnoreCase(ItemTypes.SMALL_MP_POTION.toString()))
            return new SmallManaPotion(itemData.getId());

        throw new UnknownItemIdentifierException(itemData.getType());
    }
}
