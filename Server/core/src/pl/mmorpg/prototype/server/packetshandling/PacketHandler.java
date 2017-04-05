package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;

public interface PacketHandler
{
	void handle(Object object, Connection connection);
}
