package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.data.entities.CharacterItem;
import pl.mmorpg.prototype.data.entities.ItemQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.data.entities.SpellQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.data.entities.Character;
import pl.mmorpg.prototype.data.entities.CharacterSpell;
import pl.mmorpg.prototype.data.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.data.entities.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.data.entities.repositories.CharacterRepository;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.equipment.EquipableItem;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.spells.objects.ThrowableObject;
import pl.mmorpg.prototype.server.states.PlayState;

public class UserCharacterDataPacketHandler extends PacketHandlerBase<UserCharacterDataPacket>
{
	private final CharacterRepository characterRepo = SpringContext.getBean(CharacterRepository.class);

	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private PlayState playState;
	private Server server;

	public UserCharacterDataPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId, Server server,
			PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.server = server;
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, UserCharacterDataPacket packet)
	{
		userChosenCharacter(packet.getId(), connection);
	}

	private void userChosenCharacter(int userCharacterId, Connection connection)
	{
		Character character = characterRepo.findOneAndFetchEverythingRelated(userCharacterId);

		UserInfo info = loggedUsersKeyUserId.get(character.getUser().getId());
		info.userCharacter = character;
		int clientId = connection.getID();
		sendCurrentGameObjectsInfo(connection);
		PlayerCharacter newPlayer = new PlayerCharacter(character, playState, clientId);
		Collection<Item> playerItems = getPlayerItems(newPlayer);
		playerItems.forEach(newPlayer::addItemDenyStacking);
		applyEquipmentEffects(playerItems, newPlayer);
		playState.add(newPlayer);
		server.sendToAllExceptTCP(clientId, PacketsMaker.makeCreationPacket(newPlayer));
		sendItemsDataToClient(playerItems, connection);
		sendItemQuickAccessBarConfigToClient(character.getItemQuickAccessBarConfig().values(), connection);
		sendSpellQuickAccessBarConfigToClient(character.getSpellQuickAccessBarConfig().values(), connection);
		sendQuestInfoToClient(character.getQuests(), connection);
		sendAvailableSpellsToClient(character.getSpells(), connection);
	}

	private void applyEquipmentEffects(Collection<Item> playerItems, PlayerCharacter playerCharacter)
	{
		playerItems.stream()
				.filter(EquipableItem.class::isInstance)
				.map(item -> (EquipableItem)item)
				.forEach(playerCharacter::applyEquipmentItemEffect);
	}

	private Collection<Item> getPlayerItems(PlayerCharacter newPlayer)
	{
		CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);

		return itemRepo.findByCharacter_Id((int) newPlayer.getId()).stream()
				.map(this::convertToGameItem)
				.collect(Collectors.toList());
	}

	private Item convertToGameItem(CharacterItem item)
	{
		return GameItemsFactory.produce(item, IdSupplier.getId());
	}

	private void sendCurrentGameObjectsInfo(Connection connection)
	{
		Map<Long, GameObject> gameObjects = playState.getGameObjects();
		for (GameObject object : gameObjects.values())
		{
			if (object instanceof PlayerCharacter)
				connection.sendTCP(PacketsMaker.makeCreationPacket((PlayerCharacter) object));
			else if (object instanceof Monster)
				connection.sendTCP(PacketsMaker.makeCreationPacket((Monster) object));
			else if (object instanceof ThrowableObject)
				connection.sendTCP(PacketsMaker.makeCreationPacket((ThrowableObject)object, ((ThrowableObject)object).getTarget()));
			else
				connection.sendTCP(PacketsMaker.makeCreationPacket(object));
		}
	}

	private void sendItemsDataToClient(Collection<Item> playerItems, Connection connection)
	{
		playerItems.forEach((item) -> connection.sendTCP(PacketsMaker.makeItemPacket(item)));
	}

	private void sendItemQuickAccessBarConfigToClient(
			Collection<ItemQuickAccessBarConfigurationElement> itemQuickAccessBarConfig, Connection connection)
	{
		itemQuickAccessBarConfig
				.forEach(element -> connection.sendTCP(PacketsMaker.makeItemQuickAccessBarConfigElementPacket(element)));
	}

	private void sendSpellQuickAccessBarConfigToClient(
			Collection<SpellQuickAccessBarConfigurationElement> spellQuickAccessBarConfig, Connection connection)
	{
		spellQuickAccessBarConfig
				.forEach(element -> connection.sendTCP(PacketsMaker.makeSpellQuickAccessBarConfigElementPacket(element)));
	}

	private void sendQuestInfoToClient(Collection<CharactersQuests> quests, Connection connection)
	{
		QuestStateInfoPacket[] questStateInfoPackets = PacketsMaker.makeQuestStateInfoPackets(quests);
		connection.sendTCP(questStateInfoPackets);
	}

	private void sendAvailableSpellsToClient(Collection<CharacterSpell> spells, Connection connection)
	{
		spells.forEach(spell -> connection.sendTCP(PacketsMaker.makeKnownSpellInfoPacket(spell)));
	}

}
