package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.exceptions.ObjectClassIsntSupportedException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.server.states.PlayState;

public class GameObjectsFactory
{
	private final PixelCollisionMap<GameObject> collisionMap;
	private final PlayState linkedState;

	public GameObjectsFactory(PixelCollisionMap<GameObject> collisionMap, PlayState linkedState)
	{
		this.collisionMap = collisionMap;
		this.linkedState = linkedState;
	}
	
	public GameObject produce(Class<? extends GameObject> objectClass, long id)
	{
		if(objectClass.equals(GreenDragon.class))
			return new GreenDragon(id, collisionMap, linkedState);
		else if(objectClass.equals(RedDragon.class))
			return new RedDragon(id, collisionMap, linkedState);
		else if(objectClass.equals(Skeleton.class))
			return new Skeleton(id, collisionMap, linkedState);
		else if(objectClass.equals(Snake.class))
			return new Snake(id, collisionMap, linkedState);
		else if(objectClass.equals(GrayDragon.class))
			return new GrayDragon(id, collisionMap, linkedState);
		else if(objectClass.equals(QuestBoard.class))
			return new QuestBoard(id);
		
		throw new ObjectClassIsntSupportedException(objectClass);		
	}
}
