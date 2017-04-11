package pl.mmorpg.prototype.server.objects.items;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ItemTypes;

public class ItemIdentifier
{
    private static Map<Class<?>, ItemTypes> identifiers = new HashMap<>();
    
    static
    {
        identifiers.put(SmallHpPotion.class, ItemTypes.SMALL_HP_POTION);
        identifiers.put(SmallMpPotion.class, ItemTypes.SMALL_MP_POTION);
    }

    public static ItemTypes getIdentifier(Class<? extends Item> clazz)
    {
        return identifiers.get(clazz);
    }
}
