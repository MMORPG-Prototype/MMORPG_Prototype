package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Map;
import java.util.TreeMap;

import pl.mmorpg.prototype.clientservercommon.ObjectsIdentifiers;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;

public class MonsterIdentifier
{
	private static final Map<String, Class<? extends Monster>> monsterTypes = new TreeMap<>();
	
	static
	{
		monsterTypes.put(ObjectsIdentifiers.GREEN_DRAGON, GreenDragon.class);
		monsterTypes.put(ObjectsIdentifiers.RED_DRAGON, RedDragon.class);
		monsterTypes.put(ObjectsIdentifiers.SKELETON, Skeleton.class);
		monsterTypes.put(ObjectsIdentifiers.SNAKE, Snake.class);
		monsterTypes.put(ObjectsIdentifiers.GRAY_DRAGON, GrayDragon.class);
	}
	
	public static Class<? extends Monster> getMonsterType(String identifier)
	{
		Class<? extends Monster> monsterType = monsterTypes.get(identifier);
		if(monsterType == null)
			throw new UnknownIdentifierException(identifier);
		return monsterType;
	}
	
}
