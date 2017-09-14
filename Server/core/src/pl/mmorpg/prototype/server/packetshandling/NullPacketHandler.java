package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.server.quests.events.Event;

public class NullPacketHandler implements PacketHandler
{

	@Override
	public Collection<Event> handle(Object object, Connection connection)
	{
        return Collections.emptyList();
	}

}
