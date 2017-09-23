package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;

public class NullPacketHandler implements PacketHandler
{

	@Override
	public void handle(Object object, Connection connection)
	{
	}

}
