package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemTypes;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;

public class GameItemsFactory
{

    public static Item produce(CharacterItem item, long gameId)
    {
        ItemTypes itemType = item.getType();
        if(itemType.equals(ItemTypes.SMALL_HP_POTION))
            return new SmallHpPotion(gameId);
        else if(itemType.equals(ItemTypes.SMALL_MP_POTION))
            return new SmallMpPotion(gameId);
        
        throw new UnknownItemTypeException(itemType);
    }

}
