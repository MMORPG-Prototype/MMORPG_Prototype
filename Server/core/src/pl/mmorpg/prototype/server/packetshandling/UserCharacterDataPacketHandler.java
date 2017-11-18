package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.ItemQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.SpellQuickAccessBarConfigurationElement;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.UserCharacterSpell;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.database.repositories.CharacterItemRepository;
import pl.mmorpg.prototype.server.database.repositories.UserCharacterRepository;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.PlayState;

public class UserCharacterDataPacketHandler extends PacketHandlerBase<UserCharacterDataPacket>
{
	private final UserCharacterRepository characterRepo = SpringContext.getBean(UserCharacterRepository.class);

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
		userChoosenCharcter(packet.getId(), connection);
	}

	private void userChoosenCharcter(int userCharacterId, Connection connection)
	{
		UserCharacter character = characterRepo.findOneAndFetchEverythingRelated(userCharacterId);

		UserInfo info = loggedUsersKeyUserId.get(character.getUser().getId());
		info.userCharacter = character;
		int clientId = connection.getID();
		sendCurrentGameObjectsInfo(connection);
		PlayerCharacter newPlayer = new PlayerCharacter(character, playState, clientId);
		Collection<Item> playerItems = getPlayerItems(newPlayer);
		playerItems.forEach((item) -> newPlayer.addItemDenyStacking(item));
		playState.add(newPlayer);
		server.sendToAllExceptTCP(clientId, PacketsMaker.makeCreationPacket(newPlayer));
		sendItemsDataToClient(playerItems, connection);
		sendItemQuickAccessBarConfigToClient(character.getItemQuickAccessBarConfig().values(), connection);
		sendSpellQuickAccessBarConfigToClient(character.getSpellQuickAccessBarConfig().values(), connection);
		sendQuestInfoToClient(character.getQuests(), connection);
		sendAvailableSpellsToClient(character.getSpells(), connection);
	}

	private Collection<Item> getPlayerItems(PlayerCharacter newPlayer)
	{
		CharacterItemRepository itemRepo = SpringContext.getBean(CharacterItemRepository.class);
		List<Item> characterItems = itemRepo.findByCharacter_Id((int) newPlayer.getId()).stream()
				.map(dbItem -> convertToGameItem(dbItem)).collect(Collectors.toList());

		return characterItems;
	}

	private Item convertToGameItem(CharacterItem item)
	{
		Item result = GameItemsFactory.produce(item, IdSupplier.getId());
		return result;
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

	private void sendAvailableSpellsToClient(Collection<UserCharacterSpell> spells, Connection connection)
	{
		spells.forEach(spell -> connection.sendTCP(PacketsMaker.makeKnownSpellInfoPacket(spell)));
	}

}
