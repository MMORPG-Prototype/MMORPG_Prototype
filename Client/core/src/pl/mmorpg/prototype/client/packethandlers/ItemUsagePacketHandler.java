package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ItemUsagePacket;

public class ItemUsagePacketHandler extends PacketHandlerBase<ItemUsagePacket>
{
	private PlayState playState;

	public ItemUsagePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handlePacket(ItemUsagePacket packet)
	{
		playState.itemUsagePacketReceived(packet);
	}

}
