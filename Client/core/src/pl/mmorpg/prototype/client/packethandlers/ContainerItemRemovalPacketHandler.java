package pl.mmorpg.prototype.client.packethandlers;

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
	public void handlePacket(ContainerItemRemovalPacket packet)
	{
		playState.containerItemRemovalPacketReceived(packet);
	}

}
