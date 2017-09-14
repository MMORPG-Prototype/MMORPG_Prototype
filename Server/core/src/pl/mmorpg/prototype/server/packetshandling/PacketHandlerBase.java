package pl.mmorpg.prototype.server.packetshandling;


import java.util.Collection;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.server.quests.events.Event;

public abstract class PacketHandlerBase<T> implements PacketHandler
{
	@Override
	public Collection<Event> handle(Object object, Connection connection)
	{
		@SuppressWarnings("unchecked")
		T packet = (T) object;
		return handle(connection, packet);
	}
	
	public abstract Collection<Event> handle(Connection connection, T packet);

}
