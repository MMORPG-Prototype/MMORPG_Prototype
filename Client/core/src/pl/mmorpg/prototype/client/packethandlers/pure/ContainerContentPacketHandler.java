package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ContainerContentPacket;

public class ContainerContentPacketHandler extends PacketHandlerBase<ContainerContentPacket>
{
	private PlayState playState;

	public ContainerContentPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ContainerContentPacket packet)
	{
		playState.containerOpened(packet.getContentItems(), packet.getGoldAmount(), packet.getContainerId());		
	}

}
