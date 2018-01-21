package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.ContainerGoldRemovalPacket;

public class ContainerGoldRemovalPacketHandler extends PacketHandlerAdapter<ContainerGoldRemovalPacket>
{
	private PlayState playState;

	public ContainerGoldRemovalPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(ContainerGoldRemovalPacket packet)
	{
		playState.decreaseGoldFromDialogInterface(packet.getContainerId(), packet.getGoldAmount());
	}

}
