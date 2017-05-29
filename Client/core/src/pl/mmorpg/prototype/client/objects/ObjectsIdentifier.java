package pl.mmorpg.prototype.client.objects;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.client.objects.monsters.GreenDragon;
import pl.mmorpg.prototype.client.objects.monsters.RedDragon;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.client.objects.spells.FireBall;
import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;

public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(Player.class, ObjectsIdentifiers.PLAYER);
        identifiers.put(GreenDragon.class, ObjectsIdentifiers.GREEN_DRAGON);
        identifiers.put(GreenDragonBody.class, ObjectsIdentifiers.GREEN_DRAGON_DEAD);
        identifiers.put(RedDragon.class, ObjectsIdentifiers.RED_DRAGON);
        identifiers.put(RedDragonBody.class, ObjectsIdentifiers.RED_DRAGON);
        identifiers.put(FireBall.class, ObjectsIdentifiers.FIREBALL);
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
