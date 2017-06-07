package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.exceptions.UnknownItemTypeException;
import pl.mmorpg.prototype.server.objects.items.food.BlueBerry;
import pl.mmorpg.prototype.server.objects.items.potions.SmallHpPotion;
import pl.mmorpg.prototype.server.objects.items.potions.SmallMpPotion;

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
        else if(identifier.equals(ItemIdentifiers.BLUE_BERRY))
        	return new BlueBerry(gameId, itemCount);
        
        throw new UnknownItemTypeException(identifier);
    }

}
