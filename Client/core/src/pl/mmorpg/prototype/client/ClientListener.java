package pl.mmorpg.prototype.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerDispatcher;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ClientListener extends Listener
{
	private final PacketHandlerDispatcher packetHandlerDispatcher;

	public ClientListener(final PacketHandlerDispatcher packetHandlerDispatcher)
	{
		this.packetHandlerDispatcher = packetHandlerDispatcher;
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
	public void received(Connection connection, Object packet)
	{
		packetHandlerDispatcher.dispatchPacket(packet);
		if(!(packet instanceof ObjectRepositionPacket))
			Log.info("Packet received from server, id: " + connection.getID() + "packet: " + packet);
	}

	public void tryHandlingUnhandledPackets()
	{
		packetHandlerDispatcher.tryHandlingUnhandledPackets();
	}
}
