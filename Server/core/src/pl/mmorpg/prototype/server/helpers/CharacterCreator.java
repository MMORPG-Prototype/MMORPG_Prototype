package pl.mmorpg.prototype.server.helpers;

import com.google.common.collect.Lists;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.Character;
import pl.mmorpg.prototype.server.database.repositories.CharacterRepository;
import pl.mmorpg.prototype.server.database.repositories.CharacterSpellRepository;
import pl.mmorpg.prototype.server.database.repositories.UserRepository;

public class CharacterCreator
{

	public static CharacterCreationReplyPacket tryCreatingCharacter(CharacterCreationPacket packet, int userId)
	{
		CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
		CharacterCreationReplyPacket replyPacket = new CharacterCreationReplyPacket();
		if(characterRepo.findByNickname(packet.getNickname()) != null)
		{
			replyPacket.setErrorMessage("There is already player with this nickname!");
			return replyPacket;
		}	
		
		UserRepository userRepo = SpringContext.getBean(UserRepository.class);
		Character character = new Character();
		character.setNickname(packet.getNickname());
		character.setUser(userRepo.findOne(userId));
		CharacterSpellRepository spellRepo = SpringContext.getBean(CharacterSpellRepository.class);
		character.setSpells(Lists.newArrayList(spellRepo.findAll()));
		characterRepo.save(character);
		replyPacket.setCharacter(PacketsMaker.makeCharacterPacket(character));
		replyPacket.setCreated(true);

		return replyPacket;
	}
}
