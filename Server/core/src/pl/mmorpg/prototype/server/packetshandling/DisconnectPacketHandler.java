package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.DisconnectPacket;

public class DisconnectPacketHandler extends PacketHandlerBase<DisconnectPacket>
{
	@Override
	public void handle(Connection connection, DisconnectPacket packet)
	{
		connection.close();		
	}
}
