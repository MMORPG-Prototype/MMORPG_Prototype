package pl.mmorpg.prototype.server.communication;

import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
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
		packet.setId			(character.getId());
		packet.setLevel			(character.getLevel());
		packet.setHitPoints		(character.getHitPoints());
		packet.setManaPoints	(character.getManaPoints());		
		packet.setNickname		(character.getNickname());
		packet.setExperience	(character.getExperience());
		packet.setStrength		(character.getStrength());
		packet.setMagic			(character.getMagic());
		packet.setDexitirity	(character.getDexitirity());
		packet.setGold			(character.getGold());
		return packet;
	}

	public static CharacterItemDataPacket makeItemPacket(CharacterItem item)
	{
		CharacterItemDataPacket packet = new CharacterItemDataPacket();
		packet.setId			(item.getId());
		packet.setCharacterId	(item.getCharacter().getId());
		packet.setName			(item.getName());
		packet.setType			(item.getType().toString());
		return packet;
	}
}
