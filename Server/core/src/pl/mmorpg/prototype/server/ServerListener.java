package pl.mmorpg.prototype.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.server.states.PlayState;

public class ServerListener extends Listener
{
	private Server server;
	private PlayState playState;

	public ServerListener(Server server, PlayState playState)
	{
		this.server = server;
		this.playState = playState;
	}

	@Override
	public void connected(Connection connection)
	{
		Log.info("User connected, id: " + connection.getID());
		super.connected(connection);
	}

	@Override
	public void disconnected(Connection connection)
	{
		Log.info("User disconnected, id: " + connection.getID());
		super.disconnected(connection);
	}

	@Override
	public void received(Connection connection, Object object)
	{
		Log.info("Packet received, client id: " + connection.getID() + ", packet: " + object);
		super.received(connection, object);
	}

}
