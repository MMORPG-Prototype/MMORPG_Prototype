package pl.mmorpg.prototype.client.objects.monsters;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.client.exceptions.ObjectIdentifierNotFoundException;
import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.objects.ObjectsFactory;
import pl.mmorpg.prototype.client.objects.ObjectsIdentifier;
import pl.mmorpg.prototype.client.objects.monsters.dragons.GrayDragon;
import pl.mmorpg.prototype.client.objects.monsters.dragons.GreenDragon;
import pl.mmorpg.prototype.client.objects.monsters.dragons.RedDragon;
import pl.mmorpg.prototype.client.objects.monsters.npcs.GroceryShopNpc;
import pl.mmorpg.prototype.client.objects.monsters.npcs.QuestDialogNpc;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;

public class MonstersFactory extends ObjectsFactory
{
    public MonstersFactory(PacketHandlerRegisterer registerer)
	{
		super(registerer);
	}

	public Monster produce(MonsterCreationPacket packet, CollisionMap<GameObject> linkedCollisionMap)
    {
		Monster monster = produce(packet.id, packet.identifier, packet.properties, linkedCollisionMap);
		initializePosition(packet.x, packet.y, monster);
		return monster;
	}

	private Monster produce(long id, String identifier, MonsterProperties properties,
			CollisionMap<GameObject> linkedCollisionMap)
	{
		if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(GreenDragon.class)))
			return new GreenDragon(id, properties, linkedCollisionMap, registerer);
		else if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(RedDragon.class)))
			return new RedDragon(id, properties, linkedCollisionMap, registerer);
		else if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(GrayDragon.class)))
			return new GrayDragon(id, properties, linkedCollisionMap, registerer);
		else if (identifier.equals(ObjectsIdentifier.getObjectIdentifier(Skeleton.class)))
			return new Skeleton(id, properties, linkedCollisionMap, registerer);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(Snake.class)))
			return new Snake(id, properties, linkedCollisionMap, registerer);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(GroceryShopNpc.class)))
			return new GroceryShopNpc(id, properties, linkedCollisionMap, registerer);
		else if(identifier.equals(ObjectsIdentifier.getObjectIdentifier(QuestDialogNpc.class)))
			return new QuestDialogNpc(id, properties, linkedCollisionMap, registerer);
		throw new ObjectIdentifierNotFoundException(identifier);
	}

} 
