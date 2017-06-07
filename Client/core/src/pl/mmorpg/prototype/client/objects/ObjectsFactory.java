package pl.mmorpg.prototype.client.objects;


import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.client.objects.monsters.GreenDragon;
import pl.mmorpg.prototype.client.objects.monsters.RedDragon;
import pl.mmorpg.prototype.client.objects.monsters.Skeleton;
import pl.mmorpg.prototype.client.objects.monsters.bodies.GreenDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.RedDragonBody;
import pl.mmorpg.prototype.client.objects.monsters.bodies.SkeletonBody;
import pl.mmorpg.prototype.client.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.client.objects.spells.FireBall;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;

public class ObjectsFactory
{
    public GameObject produce(ObjectCreationPacket packet)
    {
        return produce(packet.identifier, packet.id, packet.x, packet.y);
    }

    public GameObject produce(String identifier, long id, float x, float y)
    {
        GameObject object = null;
        if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(Player.class)))
            object = new Player(id);
        else if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(GreenDragon.class)))
            object = new GreenDragon(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(GreenDragonBody.class)))
        	object = new GreenDragonBody(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(RedDragon.class)))
        	object = new RedDragon(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(RedDragonBody.class)))
        	object = new RedDragonBody(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(FireBall.class)))
            object = new FireBall(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(Skeleton.class)))
        	object = new Skeleton(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(SkeletonBody.class)))
        	object = new SkeletonBody(id);
        else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(GroceryShopNpc.class)))
        	object = new GroceryShopNpc(id);
        else
            throw new ObjectIdentifierNotFoundException(identifier);

        if (object instanceof MovableGameObject)
            ((MovableGameObject) object).initPosition(x, y);
        object.setPosition(x, y);
        return object;
    }

    private static class ObjectIdentifierNotFoundException extends GameException
    {
        public ObjectIdentifierNotFoundException(String identifier)
        {
            super("Identifier: " + identifier);
        }
    }
}
