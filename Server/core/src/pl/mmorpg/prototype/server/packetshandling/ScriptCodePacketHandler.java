package pl.mmorpg.prototype.server.packetshandling;

import javax.script.ScriptException;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.packets.ScriptCodePacket;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.states.PlayState;

public class ScriptCodePacketHandler extends PacketHandlerBase<ScriptCodePacket>
{
	private PlayState playState;

	public ScriptCodePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(Connection connection, ScriptCodePacket packet)
	{
		try
		{
			playState.executeCode(packet.getCode());			
		}catch(ScriptException e)
		{
			connection.sendTCP(PacketsMaker.makeScriptExecutionErrorPacket(e.getMessage()));
		}
	}

}
