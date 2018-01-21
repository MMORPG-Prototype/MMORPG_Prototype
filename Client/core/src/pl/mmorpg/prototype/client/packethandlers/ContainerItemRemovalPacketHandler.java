package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerItemRemovalPacket;

public class ContainerItemRemovalPacketHandler extends PacketHandlerAdapter<ContainerItemRemovalPacket>
{
	private PlayState playState;

	public ContainerItemRemovalPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(ContainerItemRemovalPacket packet)
	{
		playState.containerItemRemovalPacketReceived(packet);
	}

}
