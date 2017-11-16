package pl.mmorpg.prototype.server.objects;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.Skeleton;
import pl.mmorpg.prototype.server.objects.monsters.Snake;
import pl.mmorpg.prototype.server.objects.monsters.bodies.GrayDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.server.objects.monsters.bodies.SnakeBody;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.server.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.server.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.server.objects.monsters.spells.objects.Fireball;

public class ObjectsIdentifier
{
    private static BiMap<Class<? extends GameObject>, String> identifiers = HashBiMap.create();

    static
    {
        identifiers.put(PlayerCharacter.class, ObjectsIdentifiers.PLAYER);
        identifiers.put(GreenDragon.class, ObjectsIdentifiers.GREEN_DRAGON);
        identifiers.put(GreenDragonBody.class, ObjectsIdentifiers.GREEN_DRAGON_DEAD);
        identifiers.put(RedDragon.class, ObjectsIdentifiers.RED_DRAGON);
        identifiers.put(RedDragonBody.class, ObjectsIdentifiers.RED_DRAGON_DEAD);
        identifiers.put(GrayDragon.class, ObjectsIdentifiers.GRAY_DRAGON);
        identifiers.put(GrayDragonBody.class, ObjectsIdentifiers.GRAY_DRAGON_DEAD);
        identifiers.put(Fireball.class, ObjectsIdentifiers.FIREBALL);
        identifiers.put(Skeleton.class, ObjectsIdentifiers.SKELETON);
        identifiers.put(SkeletonBody.class, ObjectsIdentifiers.SKELETON_DEAD);
        identifiers.put(GroceryShopNpc.class, ObjectsIdentifiers.GROCERY_NPC);
        identifiers.put(QuestDialogNpc.class, ObjectsIdentifiers.QUEST_DIALOG_NPC);
        identifiers.put(Snake.class, ObjectsIdentifiers.SNAKE);
        identifiers.put(SnakeBody.class, ObjectsIdentifiers.SNAKE_DEAD);
        identifiers.put(QuestBoard.class, ObjectsIdentifiers.QUEST_BOARD);
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
    
    public static Class<? extends GameObject> getObjectType(String objectIdentifier)
    {
    	return identifiers.inverse().get(objectIdentifier);
    }
}
