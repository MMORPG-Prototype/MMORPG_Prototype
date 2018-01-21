package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.UnacceptableOperationPacket;

public class UnacceptableOperationPacketHandler extends PacketHandlerAdapter<UnacceptableOperationPacket>
{
	private PlayState playState;

	public UnacceptableOperationPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(UnacceptableOperationPacket packet)
	{
		playState.showTimedErrorMessage(packet.getErrorMessage(), 5.0f);
	}

}
