package pl.mmorpg.prototype.server.communication;

import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.GameObject;

public class PacketsMaker
{
	public static ObjectCreationPacket makeCreationPacket(GameObject object)
	{
		ObjectCreationPacket packet = new ObjectCreationPacket();
		packet.id = object.getId();
		packet.identifier = object.getIdentifier();
		packet.x = object.getX();
		packet.y = object.getY();
		return packet;
	}

	public static ObjectRemovePacket makeRemovalPacket(int id)
	{
		return new ObjectRemovePacket(id);
	}

	public static ObjectRepositionPacket makeRepositionPacket(long id, float x, float y)
	{
		ObjectRepositionPacket packet = new ObjectRepositionPacket();
		packet.id = id;
		packet.x = x;
		packet.y = y;
		return packet;
	}

	public static UserCharacterDataPacket makeCharacterPacket(UserCharacter character)
	{
		UserCharacterDataPacket packet = new UserCharacterDataPacket();
		packet.id = character.getId();
		packet.level = character.getLevel();
		packet.nickname = character.getNickname();
		packet.experience = character.getExperience();
		packet.strength = character.getStrength();
		packet.magic = character.getMagic();
		packet.dexitirity = character.getDexitirity();
		return packet;
	}
}
