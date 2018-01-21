package pl.mmorpg.prototype.client.packethandlers.pure;

import java.awt.Point;

import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemStackPacket;

public class InventoryItemStackPacketHandler extends PacketHandlerBase<InventoryItemStackPacket>
{
	private PlayState playState;

	public InventoryItemStackPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(InventoryItemStackPacket packet)
	{
		ItemInventoryPosition firstPosition = new ItemInventoryPosition(packet.getFirstPositionPageNumber(),
				new Point(packet.getFirstPositionPageX(), packet.getFirstPositionPageY()));
		ItemInventoryPosition secondPosition = new ItemInventoryPosition(packet.getSecondPositionPageNumber(),
				new Point(packet.getSecondPositionPageX(), packet.getSecondPositionPageY()));
		
		playState.inventoryItemStackPacketReceived(firstPosition, secondPosition);
	}

}