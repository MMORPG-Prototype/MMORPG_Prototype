package pl.mmorpg.prototype.server.packetshandling;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticatonReplyPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.helpers.Authenticator;

public class AuthenticationPacketHandler extends PacketHandlerBase<AuthenticationPacket>
{
	private final Map<Integer, UserInfo> loggedUsersKeyUserId;
	private final Map<Integer, User> authenticatedClientsKeyClientId;
	private final Server server;

	public AuthenticationPacketHandler(Map<Integer, UserInfo> loggedUsersKeyUserId,
			Map<Integer, User> authenticatedClientsKeyClientId,
			Server server)
	{
		this.loggedUsersKeyUserId = loggedUsersKeyUserId;
		this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
		this.server = server;
	}

	@Override
	public void handle(Connection connection, AuthenticationPacket packet)
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
}
