package pl.mmorpg.prototype.server.communication;

import pl.mmorpg.prototype.clientservercommon.packets.MonsterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.ObjectRemovePacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.CharacterItemDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ExperienceGainPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterDamagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.MonsterTargetingReplyPacket;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

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
	
	public static MonsterCreationPacket makeCreationPacket(Monster monster)
	{
		MonsterCreationPacket packet = new MonsterCreationPacket();
		packet.id = monster.getId();
		packet.identifier = monster.getIdentifier();
		packet.x = monster.getX();
		packet.y = monster.getY();
		packet.properties = monster.getProperites();
		return packet;
	}


	public static ObjectRemovePacket makeRemovalPacket(long id)
	{
		return new ObjectRemovePacket(id);
	}

	public static ObjectRepositionPacket makeRepositionPacket(GameObject gameObject)
	{
		return makeRepositionPacket(gameObject.getId(), gameObject.getX(), gameObject.getY());
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

	public static MonsterTargetingReplyPacket makeTargetingReplyPacket(GameObject target)
	{
		if(target == null) 
			throw new NullPointerException("Target cannot be null");
		MonsterTargetingReplyPacket packet = new MonsterTargetingReplyPacket();
		packet.monsterId = target.getId();
		return packet;
	}

	public static MonsterDamagePacket makeDamagePacket(long id, int damage)
	{
		MonsterDamagePacket packet = new MonsterDamagePacket();
		packet.setTargetId(id);
		packet.setDamage(damage);
		return packet;
	}

	public static ExperienceGainPacket makeExperienceGainPacket(long id, int experienceGain)
	{
		ExperienceGainPacket packet = new ExperienceGainPacket();
		packet.setTargetId(id);
		packet.setExperience(experienceGain);
		return packet;
	}
}
