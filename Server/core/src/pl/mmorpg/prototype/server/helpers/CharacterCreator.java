package pl.mmorpg.prototype.server.helpers;

import com.google.common.collect.Lists;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.CharacterCreationReplyPacket;
import pl.mmorpg.prototype.data.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.data.entities.repositories.*;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.CharacterItem;
import pl.mmorpg.prototype.data.entities.components.InventoryPosition;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CharacterCreator
{

	public static CharacterCreationReplyPacket tryCreatingCharacter(CharacterCreationPacket packet, int userId)
	{
		CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);
		CharacterCreationReplyPacket replyPacket = new CharacterCreationReplyPacket();
		if (characterRepo.findByNickname(packet.getNickname()) != null)
		{
			replyPacket.setErrorMessage("There is already player with this nickname!");
			return replyPacket;
		}

		UserRepository userRepo = SpringContext.getBean(UserRepository.class);
		Character character = new Character();
		character.setNickname(packet.getNickname());
		character.setUser(userRepo.findById(userId).get());
		CharacterSpellRepository spellRepo = SpringContext.getBean(CharacterSpellRepository.class);
		character.setSpells(Lists.newArrayList(spellRepo.findAll()));
		List<CharactersQuests> quests = StreamSupport
				.stream(SpringContext.getBean(QuestRepository.class).findAllFetchItemReward().spliterator(), false)
				.map(quest -> new CharactersQuests(character, quest))
				.collect(Collectors.toList());
		character.setQuests(quests);
		characterRepo.save(character);
		addDebugThings(character);
		replyPacket.setCharacter(PacketsMaker.makeCharacterPacket(character));
		replyPacket.setCreated(true);

		return replyPacket;
	}

	private static void addDebugThings(Character character)
	{
		CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);
		CharacterItem item = new CharacterItem();
		item.setInventoryPosition(new InventoryPosition(0, 1, 0));
		item.setCharacter(character);
		item.setIdentifier(ItemIdentifiers.SWORD);
		itemRepo.save(item);
	}
}
