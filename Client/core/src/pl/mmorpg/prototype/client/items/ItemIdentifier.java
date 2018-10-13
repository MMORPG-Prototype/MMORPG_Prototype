package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.items.equipment.Armor;
import pl.mmorpg.prototype.client.items.equipment.Sword;
import pl.mmorpg.prototype.client.items.food.BlueBerry;
import pl.mmorpg.prototype.client.items.food.Fish;
import pl.mmorpg.prototype.client.items.potions.SmallHealthPotion;
import pl.mmorpg.prototype.client.items.potions.SmallManaPotion;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;

import java.util.HashMap;
import java.util.Map;

public class ItemIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(SmallHealthPotion.class, ItemIdentifiers.SMALL_HP_POTION.toString());
        identifiers.put(SmallManaPotion.class, ItemIdentifiers.SMALL_MP_POTION.toString());
        identifiers.put(BlueBerry.class, ItemIdentifiers.BLUE_BERRY.toString());
        identifiers.put(Fish.class, ItemIdentifiers.FISH.toString());
        identifiers.put(Sword.class, ItemIdentifiers.SWORD.toString());
        identifiers.put(Armor.class, ItemIdentifiers.ARMOR.toString());
    }

    public static String getIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
