package pl.mmorpg.prototype.server.helpers;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.database.repositories.UserRepository;

public class CharacterCreator
{

	public static CharacterCreationReplyPacket tryCreatingCharacter(CharacterCreationPacket packet, int userId)
	{
		UserCharacterRepository characterRepo = SpringContext.getBean(UserCharacterRepository.class);
		UserRepository userRepo = SpringContext.getBean(UserRepository.class);
		CharacterCreationReplyPacket replyPacket = new CharacterCreationReplyPacket();
		if (characterRepo.findByNickname(packet.getNickname()) == null)
		{
			UserCharacter userCharacter = new UserCharacter();
			userCharacter.setNickname(packet.getNickname());
			userCharacter.setUser(userRepo.findOne(userId));
			characterRepo.save(userCharacter);
			replyPacket.setCharacter(PacketsMaker.makeCharacterPacket(userCharacter));
			replyPacket.setCreated(true);
		} else
			replyPacket.setErrorMessage("There is already player with this nickname!");

		return replyPacket;
	}
}
