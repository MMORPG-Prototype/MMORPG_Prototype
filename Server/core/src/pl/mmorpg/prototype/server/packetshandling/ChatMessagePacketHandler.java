package pl.mmorpg.prototype.server.packetshandling;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.clientservercommon.packets.ChatMessageReplyPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class ChatMessagePacketHandler extends PacketHandlerBase<ChatMessagePacket>
{
	private Server server;
	private Map<Integer, User> authenticatedClientsKeyClientId;
	private Map<Integer, UserInfo> loggedUsersKeyUserId;

	public ChatMessagePacketHandler(Server server, Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId)
	{
		this.server = server;
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
	}

	@Override
	public void handle(Connection connection, ChatMessagePacket packet)
	{
		Field connectionsField = FieldUtils.getField(Server.class, "connections", true);
		
		Connection[] connections;
		try
		{
			connections = (Connection[])FieldUtils.readField(connectionsField, server, true);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		ChatMessageReplyPacket newPacket = new ChatMessageReplyPacket();
		String nickname = getUserCharacterNickname(connection.getID());
		newPacket.setMessage(packet.getMessage());
		newPacket.setNickname(nickname);
		UserCharacter character;
		for(Connection client : connections)		
			if((character = getUserCharacter(client.getID())) != null)
			{
				newPacket.setSourceCharacterId(character.getId());
				server.sendToTCP(client.getID(), newPacket);
			}
	}

	private UserCharacter getUserCharacter(int clientId)
	{
		if(!authenticatedClientsKeyClientId.containsKey(clientId))
			return null;
		User user = authenticatedClientsKeyClientId.get(clientId);
		Integer userId = user.getId();
		if(!loggedUsersKeyUserId.containsKey(userId))
			return null;
		return loggedUsersKeyUserId.get(userId).userCharacter;
	}

	private String getUserCharacterNickname(int clientId)
	{
		User user = authenticatedClientsKeyClientId.get(clientId);
		UserInfo userInfo = loggedUsersKeyUserId.get(user.getId());
		return userInfo.userCharacter.getNickname();
	}
}
