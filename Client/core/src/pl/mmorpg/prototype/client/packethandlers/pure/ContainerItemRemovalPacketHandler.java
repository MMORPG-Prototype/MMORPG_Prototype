package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;

public class ContainerItemRemovalPacketHandler extends PacketHandlerBase<ContainerItemRemovalPacket>
{
	private PlayState playState;

	public ContainerItemRemovalPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ContainerItemRemovalPacket packet)
	{
		playState.containerItemRemovalPacketReceived(packet);
	}

}
