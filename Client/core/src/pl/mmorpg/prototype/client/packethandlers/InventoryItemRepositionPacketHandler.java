package pl.mmorpg.prototype.client.packethandlers;

import java.awt.Point;

import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemRepositionPacket;

public class InventoryItemRepositionPacketHandler extends PacketHandlerBase<InventoryItemRepositionPacket>
{
	private PlayState playState;

	public InventoryItemRepositionPacketHandler(PlayState playState)
	{
		this.playState = playState;		
	}
	
	@Override
	public void handlePacket(InventoryItemRepositionPacket packet)
	{
		ItemInventoryPosition sourcePosition = new ItemInventoryPosition(packet.getSourcePageNumber(), new Point(packet.getSourcePageX(), packet.getSourcePageY()));
		ItemInventoryPosition destinationPosition = new ItemInventoryPosition(packet.getDestinationPageNumber(), new Point(packet.getDestinationPageX(), packet.getDestinationPageY()));
		
		playState.repositionItemInInventory(sourcePosition, destinationPosition);
		
	}

}
