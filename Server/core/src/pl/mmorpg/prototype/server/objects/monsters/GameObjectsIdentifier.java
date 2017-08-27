package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Map;
import java.util.TreeMap;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;

public class GameObjectsIdentifier
{
	private static final Map<String, Class<? extends GameObject>> objectsTypes = new TreeMap<>();
	
	static
	{
		objectsTypes.put(ObjectsIdentifiers.GREEN_DRAGON, GreenDragon.class);
		objectsTypes.put(ObjectsIdentifiers.RED_DRAGON, RedDragon.class);
		objectsTypes.put(ObjectsIdentifiers.SKELETON, Skeleton.class);
		objectsTypes.put(ObjectsIdentifiers.SNAKE, Snake.class);
		objectsTypes.put(ObjectsIdentifiers.GRAY_DRAGON, GrayDragon.class);
		objectsTypes.put(ObjectsIdentifiers.QUEST_BOARD, QuestBoard.class);
	}
	
	public static Class<? extends GameObject> getObjectType(String identifier)
	{
		Class<? extends GameObject> objectType = objectsTypes.get(identifier);
		if(objectType == null)
			throw new UnknownIdentifierException(identifier);
		return objectType;
	}
	
}
