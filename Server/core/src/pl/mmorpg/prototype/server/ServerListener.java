package pl.mmorpg.prototype.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.packetshandling.PacketHandler;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerFactory;
import pl.mmorpg.prototype.server.states.PlayState;

public class ServerListener extends Listener
{
	private final Server server;
	private final PlayState playState;
	private final Map<Integer, UserInfo> loggedUsersKeyUserId = new ConcurrentHashMap<>();
	private final Map<Integer, User> authenticatedClientsKeyClientId = new ConcurrentHashMap<>();
	private final PacketHandlerFactory packetHandlersFactory;

	public ServerListener(Server server, PlayState playState)
	{
		this.server = server;
		this.playState = playState;
		packetHandlersFactory = new PacketHandlerFactory(loggedUsersKeyUserId, authenticatedClientsKeyClientId, server, playState);
	}

	@Override
	public void connected(Connection connection)
	{
		Log.info("User connected, id: " + connection.getID());
	}

	@Override
	public void disconnected(Connection connection)
	{
		if (authenticatedClientsKeyClientId.containsKey(connection.getID()))
		{
			LogoutPacket logoutPacket = new LogoutPacket();
			PacketHandler packetHandler = packetHandlersFactory.produce(logoutPacket);
			packetHandler.handle(logoutPacket, connection);
		}

		Log.info("User disconnected, id: " + connection.getID());
		super.disconnected(connection);
	}
	

	@Override
	public void received(Connection connection, Object object)
	{
		PacketHandler packetHandler = packetHandlersFactory.produce(object);
		packetHandler.handle(object, connection);
	
		Log.info("Packet received, client id: " + connection.getID() + ", packet: " + object);
		super.received(connection, object);
	}
	
	private UserCharacter getCharacter(int clientId)
	{
		User user = authenticatedClientsKeyClientId.get(clientId);
		UserInfo info = loggedUsersKeyUserId.get(user.getId());
		return info.userCharacter;
	}
}
