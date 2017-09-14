package pl.mmorpg.prototype.server.packetshandling;

import java.util.Collection;
import java.util.Collections;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;
import pl.mmorpg.prototype.server.quests.events.Event;

public class DisconnectPacketHandler extends PacketHandlerBase<DisconnectPacket>
{
	@Override
	public Collection<Event> handle(Connection connection, DisconnectPacket packet)
	{
		connection.close();
        return Collections.emptyList();		
	}
}
