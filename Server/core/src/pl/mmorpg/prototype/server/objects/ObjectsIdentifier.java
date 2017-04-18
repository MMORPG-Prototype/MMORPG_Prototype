package pl.mmorpg.prototype.server.objects;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.monsters.Dragon;
import pl.mmorpg.prototype.server.objects.monsters.bodies.DragonBody;
import pl.mmorpg.prototype.server.objects.spells.Fireball;

public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();

    static
    {
        identifiers.put(PlayerCharacter.class, ObjectsIdentifiers.PLAYER);
        identifiers.put(Dragon.class, ObjectsIdentifiers.DRAGON);
        identifiers.put(DragonBody.class, ObjectsIdentifiers.DRAGON_DEAD);
        identifiers.put(Fireball.class, ObjectsIdentifiers.FIREBALL);
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
