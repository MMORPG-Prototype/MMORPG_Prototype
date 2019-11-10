package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.exceptions.ObjectIdentifierNotFoundException;
import pl.mmorpg.prototype.client.objects.interactive.QuestBoard;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GrayDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.SnakeBody;
import pl.mmorpg.prototype.client.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.client.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.client.objects.spells.FireBall;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectsFactory
{
	protected PacketHandlerRegisterer registerer;

	public ObjectsFactory(PacketHandlerRegisterer registerer)
	{
		this.registerer = registerer;
	}
	
    public GameObject produce(ObjectCreationPacket packet, CollisionMap<GameObject> linkedCollisionMap)
    {
        return produce(packet.identifier, packet.id, packet.x, packet.y, linkedCollisionMap);
    }

    public GameObject produce(String identifier, long id, float x, float y, CollisionMap<GameObject> linkedCollisionMap)
    {
        GameObject object = doProduce(identifier, id, linkedCollisionMap);
		initializePosition(x, y, object);
		return object;
    }

	private GameObject doProduce(String identifier, long id, CollisionMap<GameObject> linkedCollisionMap)
	{
		if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(GreenDragonBody.class)))
			return new GreenDragonBody(id);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(RedDragonBody.class)))
			return new RedDragonBody(id);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(GrayDragonBody.class)))
			return new GrayDragonBody(id);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(FireBall.class)))
			return new FireBall(id, linkedCollisionMap, registerer);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(SkeletonBody.class)))
			return new SkeletonBody(id);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(SnakeBody.class)))
			return new SnakeBody(id);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(QuestBoard.class)))
			return new QuestBoard(id, registerer);
		
		throw new ObjectIdentifierNotFoundException(identifier);
	}

	protected void initializePosition(float x, float y, GameObject object)
	{
		if (object instanceof MovableGameObject)
			((MovableGameObject) object).initPosition(x, y);
		object.setPosition(x, y);
	}

}
