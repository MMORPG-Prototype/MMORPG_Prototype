package pl.mmorpg.prototype.server;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.LogoutPacket;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.packetshandling.PacketHandler;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerFactory;
import pl.mmorpg.prototype.server.states.PlayState;

public class ServerListener extends Listener
{
    private final PacketHandlerFactory packetHandlersFactory;
    private Map<Integer, User> authenticatedClientsKeyClientId;

    public ServerListener(Map<Integer, UserInfo> loggedUsersKeyUserId,
            Map<Integer, User> authenticatedClientsKeyClientId, Server server, PlayState playState)
    {
        this.authenticatedClientsKeyClientId = authenticatedClientsKeyClientId;
        packetHandlersFactory = new PacketHandlerFactory(loggedUsersKeyUserId, authenticatedClientsKeyClientId, server,
                playState);
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
    }

    @Override
    public void received(Connection connection, Object object)
    {
        PacketHandler packetHandler = packetHandlersFactory.produce(object);
        packetHandler.handle(object, connection);
        Log.info("Packet received, client id: " + connection.getID() + ", packet: " + object);
    }

}
