package pl.mmorpg.prototype.server.packetshandling.characteractions;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.packets.playeractions.OpenContainterPacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.objects.containers.GameContainer;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.states.PlayState;

public class OpenContainerPacketHandler extends PacketHandlerBase<OpenContainterPacket>
{
	private PlayState playState;

	public OpenContainerPacketHandler(Server server, PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(Connection connection, OpenContainterPacket packet)
	{
		if(playState.hasContainer(packet.gameX, packet.gameY))
		{
			GameContainer container = playState.getContainer(packet.gameX, packet.gameY);
			connection.sendTCP(PacketsMaker.makeOpenContainerPacket(container));
		}
		Log.info("OpenContainer packet received" + packet.gameX + " " + packet.gameY);
	}  

}
