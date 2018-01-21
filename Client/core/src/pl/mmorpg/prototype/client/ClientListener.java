package pl.mmorpg.prototype.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.packethandlers.PacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ClientListener extends Listener
{
	private final Collection<PacketInfo> unhandledPackets = new ConcurrentLinkedQueue<>();
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
	public void received(Connection connection, Object packet)
	{
		@SuppressWarnings("unchecked")
		PacketHandler<Object> packetHandler = (PacketHandler<Object>) packetHandlersFactory.produce(packet);
		if (packetHandler.canBeHandled(packet))
			packetHandler.handle(packet);
		else if(!packetHandler.canBeOmmited(packet))
			unhandledPackets.add(new PacketInfo(connection.getID(), packet));
		
		if(!(packet instanceof ObjectRepositionPacket))
			Log.info("Packet received from server, id: " + connection.getID() + "packet: " + packet);
	}
	

	public void tryHandlingUnhandledPackets()
	{
		if(unhandledPackets.size() > 10)
			System.out.println("Unhandled packets number exceded 10 and is: " + unhandledPackets.size());
		Iterator<PacketInfo> it = unhandledPackets.iterator();
		while(it.hasNext())
		{ 
			PacketInfo packetInfo = it.next();
			@SuppressWarnings("unchecked")
			PacketHandler<Object> packetHandler = (PacketHandler<Object>) packetHandlersFactory.produce(packetInfo.packet);
			if (packetHandler.canBeHandled(packetInfo.packet))
			{
				packetHandler.handle(packetInfo.packet);
				it.remove();
			}
		}
	}

}
