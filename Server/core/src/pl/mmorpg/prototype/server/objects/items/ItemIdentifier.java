package pl.mmorpg.prototype.server.objects.items;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.server.objects.items.food.BlueBerry;
import pl.mmorpg.prototype.server.objects.items.potions.SmallHpPotion;
import pl.mmorpg.prototype.server.objects.items.potions.SmallMpPotion;

public class ItemIdentifier
{
    private static Map<Class<?>, ItemIdentifiers> identifiers = new HashMap<>();
    
    static
    {
        identifiers.put(SmallHpPotion.class, ItemIdentifiers.SMALL_HP_POTION);
        identifiers.put(SmallMpPotion.class, ItemIdentifiers.SMALL_MP_POTION);
        identifiers.put(BlueBerry.class, ItemIdentifiers.BLUE_BERRY);
    }

    public static ItemIdentifiers getIdentifier(Class<? extends Item> clazz)
    {
        return identifiers.get(clazz);
    }
}
