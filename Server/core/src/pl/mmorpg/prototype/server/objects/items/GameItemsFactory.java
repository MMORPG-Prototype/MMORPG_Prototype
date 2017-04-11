package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;

public class GameItemsFactory
{

    public static Item produce(CharacterItem item, long gameId)
    {
        ItemIdentifiers itemIdentifier = item.getIdentifier();
        if(itemIdentifier.equals(ItemIdentifiers.SMALL_HP_POTION))
            return new SmallHpPotion(gameId);
        else if(itemIdentifier.equals(ItemIdentifiers.SMALL_MP_POTION))
            return new SmallMpPotion(gameId);
        
        throw new UnknownItemTypeException(itemIdentifier);
    }

}
