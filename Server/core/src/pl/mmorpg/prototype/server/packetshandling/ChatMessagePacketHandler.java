package pl.mmorpg.prototype.server.packetshandling;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.ChatMessagePacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;

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
		String nickname = getUserCharacterNickname(connection.getID());
		packet.setNickname(nickname);
		for(Connection client : connections)		
			if(isUserInGame(client.getID()))
				server.sendToTCP(client.getID(), packet);
	}

	private boolean isUserInGame(int clientId)
	{
		if(!authenticatedClientsKeyClientId.containsKey(clientId))
			return false;
		User user = authenticatedClientsKeyClientId.get(clientId);
		Integer userId = user.getId();
		if(!loggedUsersKeyUserId.containsKey(userId))
			return false;
		return true;
	}

	private String getUserCharacterNickname(int clientId)
	{
		User user = authenticatedClientsKeyClientId.get(clientId);
		UserInfo userInfo = loggedUsersKeyUserId.get(user.getId());
		return userInfo.userCharacter.getNickname();
	}
}
