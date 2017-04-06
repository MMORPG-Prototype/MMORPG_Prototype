package pl.mmorpg.prototype.server.objects;


import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.map.GreenGrass;

public class ObjectsIdentifier
{
    private static Map<Class<?>, String> identifiers = new HashMap<>();
    
    static
    {
		identifiers.put(PlayerCharacter.class, ObjectsIdentifiers.PLAYER);
		identifiers.put(GreenGrass.class, ObjectsIdentifiers.GREEN_GRASS);
    }
    
    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
