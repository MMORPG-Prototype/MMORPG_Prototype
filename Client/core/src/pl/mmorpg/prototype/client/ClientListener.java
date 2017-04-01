package pl.mmorpg.prototype.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class ClientListener extends Listener
{

	private GameClient gameClient;

	public ClientListener(GameClient gameClient)
	{
		this.gameClient = gameClient;
	}

	@Override
	public void connected(Connection connection)
	{
		Log.info("Connected to server, id: " + connection.getID());
	}

	@Override
	public void disconnected(Connection connection)
	{
		Log.info("Disconnected from server, id: " + connection.getID());
	}

	@Override
	public void received(Connection connection, Object object)
	{
		Log.info("Packet received from server, id: " + connection.getID() + "packet: " + object);
	}

}
