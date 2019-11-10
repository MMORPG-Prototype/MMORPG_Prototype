package pl.mmorpg.prototype.server.objects.monsters;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.exceptions.ObjectClassIsntSupportedException;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.ineractivestaticobjects.QuestBoard;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.server.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.server.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.server.objects.monsters.npcs.QuestDialogNpc;
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
	
	public GameObject produce(Class<? extends GameObject> objectClass, long id, Rectangle walkingBounds)
	{
		if(objectClass.equals(GreenDragon.class))
			return new GreenDragon(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(RedDragon.class))
			return new RedDragon(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(Skeleton.class))
			return new Skeleton(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(Snake.class))
			return new Snake(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(GrayDragon.class))
			return new GrayDragon(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(QuestBoard.class))
			return new QuestBoard(id);
		else if(objectClass.equals(QuestDialogNpc.class))
			return new QuestDialogNpc(id, walkingBounds, collisionMap, linkedState);
		else if(objectClass.equals(GroceryShopNpc.class))
			return new GroceryShopNpc(id, walkingBounds, collisionMap, linkedState);
		
		throw new ObjectClassIsntSupportedException(objectClass);		
	}
}
