package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;

public class UnacceptableOperationPacketHandler extends PacketHandlerBase<UnacceptableOperationPacket>
{
	private PlayState playState;

	public UnacceptableOperationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(UnacceptableOperationPacket packet)
	{
		playState.showTimedErrorMessage(packet.getErrorMessage(), 5.0f);
	}

}
