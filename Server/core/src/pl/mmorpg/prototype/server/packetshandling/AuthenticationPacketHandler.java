package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.AuthenticationReplyPacket;
import pl.mmorpg.prototype.server.UserInfo;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.helpers.Authenticator;
import pl.mmorpg.prototype.server.quests.events.Event;

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
	public Collection<Event> handle(Connection connection, AuthenticationPacket packet)
	{
		Authenticator authenticator = new Authenticator(loggedUsersKeyUserId, packet);
		try
		{
			authenticator.tryAuthenticating();		
		}
		catch(ExceptionInInitializerError | NoClassDefFoundError e)
		{
			Log.error("Database connection problem");
			AuthenticationReplyPacket replyPacket = new AuthenticationReplyPacket();
			replyPacket.message = "Database connection problem";
			server.sendToTCP(connection.getID(), replyPacket);
			return Collections.emptyList();
		}
		
		if (authenticator.isAuthenticated())
		{
			User user = authenticator.getUser();
			UserInfo userInfo = new UserInfo();
			userInfo.user = user;
			loggedUsersKeyUserId.put(user.getId(), userInfo);
			authenticatedClientsKeyClientId.put(connection.getID(), user);
		}
		AuthenticationReplyPacket replyPacket = authenticator.getReplyPacket();
		server.sendToTCP(connection.getID(), replyPacket);
		return Collections.emptyList();
	}
}
