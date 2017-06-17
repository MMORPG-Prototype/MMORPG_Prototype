package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptExecutionErrorPacket;

public class ScriptExecutionErrorPacketHandler extends PacketHandlerBase<ScriptExecutionErrorPacket>
{
	private PlayState playState;

	public ScriptExecutionErrorPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handlePacket(ScriptExecutionErrorPacket packet)
	{
		playState.scriptExecutionErrorReceived(packet.getError());
	}

}
