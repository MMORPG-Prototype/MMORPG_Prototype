package pl.mmorpg.prototype.server;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticatonReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.clientservercommon.packets.GetUserCharactersPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveDownPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveLeftPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveRightPacket;
import pl.mmorpg.prototype.clientservercommon.packets.movement.MoveUpPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.managers.UserCharacterTableManager;
import pl.mmorpg.prototype.server.database.managers.UserTableManager;
import pl.mmorpg.prototype.server.helpers.Authenticator;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.PlayState;

public class ServerListener extends Listener
{
	private Server server;
	private PlayState playState;
	private Map<Integer, UserInfo> loggedUsersKeyUserId = new ConcurrentHashMap<>();
	private Map<Integer, User> authenticatedClientsKeyClientId = new ConcurrentHashMap<>();

	public ServerListener(Server server, PlayState playState)
	{
		this.server = server;
		this.playState = playState;
	}

	@Override
	public void connected(Connection connection)
	{
		Log.info("User connected, id: " + connection.getID());
	}

	private void repositionNewlyAddedCharacter(long id)
	{
		server.sendToAllTCP(PacketsMaker.makeRepositionPacket(id, 100.0f, 100.0f));
	}

	private void sendCurrentGameObjectsInfo(int id)
	{
		Map<Long, GameObject> gameObjects = playState.getGameObjects();
		for (GameObject object : gameObjects.values())
			server.sendToTCP(id, PacketsMaker.makeCreationPacket(object));
	}

	@Override
	public void disconnected(Connection connection)
	{
		if (authenticatedClientsKeyClientId.containsKey(connection.getID()))
		{
			User user = authenticatedClientsKeyClientId.remove(connection.getID());
			UserCharacter userCharacter = loggedUsersKeyUserId.remove(user.getId()).userCharacter;
			if (userCharacter != null)
			{
				int characterId = userCharacter.getId();
				if (playState.has(characterId))
				{
					playState.remove(characterId);
					server.sendToAllTCP(PacketsMaker.makeRemovalPacket(characterId));
				}
			}
		}

		Log.info("User disconnected, id: " + connection.getID());
		super.disconnected(connection);
	}

	@Override
	public void received(Connection connection, Object object)
	{
		if (object instanceof AuthenticationPacket)
		{
			AuthenticationPacket packet = (AuthenticationPacket) object;
			handleAuthenticationPacket(connection, packet);
		} else if (object instanceof RegisterationPacket)
		{
			RegisterationPacket packet = (RegisterationPacket) object;
			handleRegisterationPacket(connection, packet);
		} else if (object instanceof GetUserCharactersPacket)
		{
			GetUserCharactersPacket packet = (GetUserCharactersPacket) object;
			handleGetUsersCharacterPacket(connection, packet);

		} else if (object instanceof UserCharacterDataPacket)
		{
			UserCharacterDataPacket packet = (UserCharacterDataPacket) object;
			userChoosenCharcter(packet.id, connection.getID());
		} else if (object instanceof MoveRightPacket)
		{
			MoveRightPacket packet = (MoveRightPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveRight(playState.getCollisionMap());
			server.sendToAllTCP(
					PacketsMaker.makeRepositionPacket(packet.id, operationTarget.getX(), operationTarget.getY()));
		} else if (object instanceof MoveLeftPacket)
		{
			MoveLeftPacket packet = (MoveLeftPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveLeft(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(packet.id, operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof MoveUpPacket)
		{
			MoveUpPacket packet = (MoveUpPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveUp(playState.getCollisionMap());
			server.sendToAllTCP(PacketsMaker.makeRepositionPacket(packet.id, operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof MoveDownPacket)
		{
			MoveDownPacket packet = (MoveDownPacket) object;
			MovableGameObject operationTarget = (MovableGameObject) playState.getObject(packet.id);
			operationTarget.moveDown(playState.getCollisionMap());
			server.sendToAllTCP(
					PacketsMaker.makeRepositionPacket(packet.id, operationTarget.getX(),
					operationTarget.getY()));
		} else if (object instanceof DisconnectPacket)
			connection.close();

		Log.info("Packet received, client id: " + connection.getID() + ", packet: " + object);
		super.received(connection, object);
	}

	private void handleAuthenticationPacket(Connection connection, AuthenticationPacket packet)
	{
		Authenticator authenticator = new Authenticator(loggedUsersKeyUserId, packet);
		authenticator.tryAuthenticating();
		if (authenticator.isAuthenticated())
		{
			User user = authenticator.getUser();
			UserInfo userInfo = new UserInfo();
			userInfo.user = user;
			loggedUsersKeyUserId.put(user.getId(), userInfo);
			authenticatedClientsKeyClientId.put(connection.getID(), user);
		}
		AuthenticatonReplyPacket replyPacket = authenticator.getReplyPacket();
		server.sendToTCP(connection.getID(), replyPacket);
	}

	private void handleRegisterationPacket(Connection connection, RegisterationPacket packet)
	{
		RegisterationReplyPacket replyPacket = new RegisterationReplyPacket();

		if (packet.username.length() == 0)
			replyPacket.errorMessage = "Username field cannot be empty";
		else if (packet.password.length() == 0)
			replyPacket.errorMessage = "Password cannot be empty";
		else if (!packet.password.equals(packet.passwordRepeat))
			replyPacket.errorMessage = "Passwords are not the same!";
		else if (UserTableManager.hasUser(packet.getUsername()))
			replyPacket.errorMessage = "User '" + packet.getUsername() + "' already exists!";
		else
		{
			replyPacket.isRegistered = true;
			User user = new User();
			user.setUsername(packet.getUsername());
			user.setPassword(packet.getPassword());
			UserTableManager.addUser(user);
		}
		server.sendToTCP(connection.getID(), replyPacket);
	}

	private void handleGetUsersCharacterPacket(Connection connection, GetUserCharactersPacket packet)
	{
		List<UserCharacter> userCharacters = UserCharacterTableManager.getUserCharacters(packet.username);
		UserCharacterDataPacket[] charactersPackets = new UserCharacterDataPacket[userCharacters.size()];
		int i = 0;
		for (UserCharacter character : userCharacters)
		{
			charactersPackets[i] = PacketsMaker.makeCharacterPacket(character);
			i++;
		}
		server.sendToTCP(connection.getID(), charactersPackets);
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
		repositionNewlyAddedCharacter(newPlayer.getId());
	}

	private UserCharacter getCharacter(int clientId)
	{
		User user = authenticatedClientsKeyClientId.get(clientId);
		UserInfo info = loggedUsersKeyUserId.get(user.getId());
		return info.userCharacter;
	}
}
