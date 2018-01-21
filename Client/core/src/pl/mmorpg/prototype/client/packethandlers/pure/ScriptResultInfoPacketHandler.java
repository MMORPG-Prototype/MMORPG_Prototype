package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ScriptResultInfoPacket;

public class ScriptResultInfoPacketHandler extends PacketHandlerBase<ScriptResultInfoPacket>
{
	private PlayState playState;

	public ScriptResultInfoPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
		
	@Override
	public void doHandle(ScriptResultInfoPacket packet)
	{
		playState.scriptResultInfoPacketReceived(packet);
	}

}
