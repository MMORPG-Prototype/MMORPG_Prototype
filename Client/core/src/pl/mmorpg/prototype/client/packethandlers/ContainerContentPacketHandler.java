package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;

public class ContainerContentPacketHandler extends PacketHandlerAdapter<ContainerContentPacket>
{
	private PlayState playState;

	public ContainerContentPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(ContainerContentPacket packet)
	{
		playState.containerOpened(packet.getContentItems(), packet.getGoldAmount(), packet.getContainerId());		
	}

}
