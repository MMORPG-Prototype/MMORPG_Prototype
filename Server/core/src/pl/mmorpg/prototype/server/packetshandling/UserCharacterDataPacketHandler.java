package pl.mmorpg.prototype.server.packetshandling;

import java.util.List;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.CharacterItem;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.managers.CharacterItemTableManager;
import pl.mmorpg.prototype.server.database.managers.UserCharacterTableManager;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public class UserCharacterDataPacketHandler extends PacketHandlerBase<UserCharacterDataPacket>
{
	private Map<Integer, UserInfo> loggedUsersKeyUserId;
	private PlayState playState;
	private Server server;

	public UserCharacterDataPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId, Server server, PlayState playState)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.server = server;
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, UserCharacterDataPacket packet)
	{
		userChoosenCharcter(packet.getId(), connection.getID());
	}
	
	private void userChoosenCharcter(int userCharacterId, int clientId)
	{
		UserCharacter character = UserCharacterTableManager.getUserCharacter(userCharacterId);

		UserInfo info = loggedUsersKeyUserId.get(character.getUser().getId());
		info.userCharacter = character;

		sendCurrentGameObjectsInfo(clientId);
		PlayerCharacter newPlayer = new PlayerCharacter(character);
		playState.add(newPlayer);
		server.sendToAllExceptTCP(clientId, PacketsMaker.makeCreationPacket(newPlayer));
		repositionNewlyAddedCharacter(newPlayer);
		sendItemsDataToClient(userCharacterId, clientId);
	}

	private void repositionNewlyAddedCharacter(PlayerCharacter newPlayer)
	{
		float newX = 96.0f;
		float newY = 96.0f;
		newPlayer.setPosition(newX, newY);
		server.sendToAllTCP(PacketsMaker.makeRepositionPacket(newPlayer.getId(), newX, newY));
	}

	private void sendCurrentGameObjectsInfo(int id)
	{
		Map<Long, GameObject> gameObjects = playState.getGameObjects();
		for (GameObject object : gameObjects.values())
			server.sendToTCP(id, PacketsMaker.makeCreationPacket(object));
	}
	
	private void sendItemsDataToClient(int userCharacterId, int clientId)
	{
		List<CharacterItem> characterItems = CharacterItemTableManager.getCharacterItems(userCharacterId);
		characterItems.forEach((item) -> server.sendToTCP(clientId, PacketsMaker.makeItemPacket(item)));
	}
}
