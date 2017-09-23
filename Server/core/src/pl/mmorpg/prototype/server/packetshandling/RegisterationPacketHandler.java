package pl.mmorpg.prototype.server.packetshandling;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.server.helpers.Registerator;

public class RegisterationPacketHandler extends PacketHandlerBase<RegisterationPacket>
{
	private final Server server;

	public RegisterationPacketHandler(Server server)
	{
		this.server = server;
	}
	
	@Override
	public void handle(Connection connection, RegisterationPacket registerationPacket)
	{
		Registerator registerator = new Registerator();
		RegisterationReplyPacket replyPacket = registerator.tryToRegister(registerationPacket);
		server.sendToTCP(connection.getID(), replyPacket);
	}
	
}
