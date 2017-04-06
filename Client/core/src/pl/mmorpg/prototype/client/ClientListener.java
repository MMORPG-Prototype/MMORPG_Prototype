package pl.mmorpg.prototype.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.packethandlers.PacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;

public class ClientListener extends Listener
{
	private final Collection<PacketInfo> unhandledPackets = new LinkedList<>();
	private final PacketHandlerFactory packetHandlersFactory;

	public ClientListener(final PlayState playState, final StateManager states)
	{
		packetHandlersFactory = new PacketHandlerFactory(playState, states);
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
		PacketHandler packetHandler = packetHandlersFactory.produce(object);
		if (packetHandler.canBeHandled(object))
			packetHandler.handle(object);
		else
			unhandledPackets.add(new PacketInfo(connection.getID(), object));
		
		Log.info("Packet received from server, id: " + connection.getID() + "packet: " + object);
	}

	public void tryHandlingUnhandledPackets()
	{
		Iterator<PacketInfo> it = unhandledPackets.iterator();
		while(it.hasNext())
		{
			PacketInfo packetInfo = it.next();
			PacketHandler packetHandler = packetHandlersFactory.produce(packetInfo.packet);
			if (packetHandler.canBeHandled(packetInfo.packet))
			{
				packetHandler.handle(packetInfo.packet);
				it.remove();
			}
		}
	}

}
