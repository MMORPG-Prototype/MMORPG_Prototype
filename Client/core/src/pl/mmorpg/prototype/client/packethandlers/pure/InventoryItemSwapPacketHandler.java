package pl.mmorpg.prototype.client.packethandlers.pure;

import java.awt.Point;

import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.InventoryItemSwapPacket;

public class InventoryItemSwapPacketHandler extends PacketHandlerBase<InventoryItemSwapPacket>
{
	private PlayState playState;

	public InventoryItemSwapPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void doHandle(InventoryItemSwapPacket packet)
	{
		ItemInventoryPosition firstPosition = new ItemInventoryPosition(packet.getFirstPositionPageNumber(),
				new Point(packet.getFirstPositionPageX(), packet.getFirstPositionPageY()));
		ItemInventoryPosition secondPosition = new ItemInventoryPosition(packet.getSecondPositionPageNumber(),
				new Point(packet.getSecondPositionPageX(), packet.getSecondPositionPageY()));

		playState.swapItemsInInventory(firstPosition, secondPosition);
	}

}
