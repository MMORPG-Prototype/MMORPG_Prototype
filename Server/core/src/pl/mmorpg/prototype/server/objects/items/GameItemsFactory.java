package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;

public class GameItemsFactory
{

    public static Item produce(CharacterItem item, long gameId)
    {
        return produce(item.getIdentifier(), item.getCount(), gameId);
    }
    
    public static Item produce(ItemIdentifiers identifier, int itemCount, long gameId)
    {
        if(identifier.equals(ItemIdentifiers.SMALL_HP_POTION))
            return new SmallHpPotion(gameId, itemCount);
        else if(identifier.equals(ItemIdentifiers.SMALL_MP_POTION))
            return new SmallMpPotion(gameId, itemCount);
        
        throw new UnknownItemTypeException(identifier);
    }

}
