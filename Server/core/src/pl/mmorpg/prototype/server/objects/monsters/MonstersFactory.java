package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.exceptions.MonsterClassIsntSupportedException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.server.states.PlayState;

public class MonstersFactory
{
	private final PixelCollisionMap<GameObject> collisionMap;
	private final PlayState linkedState;

	public MonstersFactory(PixelCollisionMap<GameObject> collisionMap, PlayState linkedState)
	{
		this.collisionMap = collisionMap;
		this.linkedState = linkedState;
	}
	
	public Monster produce(Class<? extends Monster> monsterClass, long id)
	{
		if(monsterClass.equals(GreenDragon.class))
			return new GreenDragon(id, collisionMap, linkedState);
		else if(monsterClass.equals(RedDragon.class))
			return new RedDragon(id, collisionMap, linkedState);
		else if(monsterClass.equals(Skeleton.class))
			return new Skeleton(id, collisionMap, linkedState);
		else if(monsterClass.equals(Snake.class))
			return new Snake(id, collisionMap, linkedState);
		else if(monsterClass.equals(GrayDragon.class))
			return new GrayDragon(id, collisionMap, linkedState);
		
		throw new MonsterClassIsntSupportedException(monsterClass);		
	}
}
