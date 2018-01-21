package pl.mmorpg.prototype.client;

import java.util.Collection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.client.packethandlers.PacketHandler;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerDispatcher;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerFactory;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.packets.movement.ObjectRepositionPacket;

public class ClientListener extends Listener
{
	private final PacketHandlerDispatcher packetHandlerDispatcher;

	public ClientListener(final PacketHandlerDispatcher packetHandlerDispatcher, final PlayState playState, final StateManager states)
	{
		this.packetHandlerDispatcher = packetHandlerDispatcher;
		registerPacketHandlers(new PacketHandlerFactory(playState, states));
	}

	private void registerPacketHandlers(PacketHandlerFactory packetHandlerFactory)
	{
		 Collection<PacketHandler<? extends Object>> packetHandlers = packetHandlerFactory.produceAll();
		 packetHandlers.forEach(this::registerPacketHandler);
	}
	
	@SuppressWarnings("unchecked")
	private void registerPacketHandler(PacketHandler<? extends Object> packetHandler)
	{
		packetHandlerDispatcher.registerHandler((PacketHandler<Object>) packetHandler);
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
