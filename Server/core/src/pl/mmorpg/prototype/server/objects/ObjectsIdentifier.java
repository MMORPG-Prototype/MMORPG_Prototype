package pl.mmorpg.prototype.server.objects;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.monsters.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.RedDragon;
import pl.mmorpg.prototype.server.objects.monsters.Skeleton;
import pl.mmorpg.prototype.server.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.server.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.server.objects.monsters.spells.Fireball;

public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(PlayerCharacter.class, ObjectsIdentifiers.PLAYER);
        identifiers.put(GreenDragon.class, ObjectsIdentifiers.GREEN_DRAGON);
        identifiers.put(GreenDragonBody.class, ObjectsIdentifiers.GREEN_DRAGON_DEAD);
        identifiers.put(RedDragon.class, ObjectsIdentifiers.RED_DRAGON);
        identifiers.put(RedDragonBody.class, ObjectsIdentifiers.RED_DRAGON_DEAD);
        identifiers.put(Fireball.class, ObjectsIdentifiers.FIREBALL);
        identifiers.put(Skeleton.class, ObjectsIdentifiers.SKELETON);
        identifiers.put(SkeletonBody.class, ObjectsIdentifiers.SKELETON_DEAD);
        identifiers.put(GroceryShopNpc.class, ObjectsIdentifiers.GROCERY_NPC);
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
