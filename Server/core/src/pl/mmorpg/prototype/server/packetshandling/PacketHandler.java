package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.server.quests.events.Event;

public interface PacketHandler
{
	Collection<Event> handle(Object object, Connection connection);
}
