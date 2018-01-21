package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerGoldRemovalPacket;

public class ContainerGoldRemovalPacketHandler extends PacketHandlerBase<ContainerGoldRemovalPacket>
{
	private PlayState playState;

	public ContainerGoldRemovalPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(ContainerGoldRemovalPacket packet)
	{
		playState.decreaseGoldFromDialogInterface(packet.getContainerId(), packet.getGoldAmount());
	}

}
