package pl.mmorpg.prototype.client.objects;

import java.util.HashMap;
import java.util.Map;

import pl.mmorpg.prototype.client.objects.interactive.QuestBoard;
import pl.mmorpg.prototype.client.objects.monsters.Skeleton;
import pl.mmorpg.prototype.client.objects.monsters.Snake;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GrayDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.SnakeBody;
import pl.mmorpg.prototype.client.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.client.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.client.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.client.objects.monsters.npcs.GroceryShopNpc;
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
        identifiers.put(RedDragonBody.class, ObjectsIdentifiers.RED_DRAGON_DEAD);
        identifiers.put(GrayDragon.class, ObjectsIdentifiers.GRAY_DRAGON);
        identifiers.put(GrayDragonBody.class, ObjectsIdentifiers.GRAY_DRAGON_DEAD);
        identifiers.put(FireBall.class, ObjectsIdentifiers.FIREBALL);
        identifiers.put(Skeleton.class, ObjectsIdentifiers.SKELETON);
        identifiers.put(SkeletonBody.class, ObjectsIdentifiers.SKELETON_DEAD);
        identifiers.put(GroceryShopNpc.class, ObjectsIdentifiers.GROCERY_NPC);
        identifiers.put(Snake.class, ObjectsIdentifiers.SNAKE);
        identifiers.put(SnakeBody.class, ObjectsIdentifiers.SNAKE_DEAD);
        identifiers.put(QuestBoard.class, ObjectsIdentifiers.QUEST_BOARD);
    }

    public static String getObjectIdentifier(Class<?> type)
    {
        return identifiers.get(type);
    }
}
