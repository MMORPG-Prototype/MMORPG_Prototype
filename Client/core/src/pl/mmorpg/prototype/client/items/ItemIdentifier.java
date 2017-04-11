package pl.mmorpg.prototype.client.items;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

public class ItemIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(SmallHealthPotion.class, ItemIdentifiers.SMALL_HP_POTION.toString());
        identifiers.put(SmallManaPotion.class, ItemIdentifiers.SMALL_MP_POTION.toString());
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
