package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;

public class GoldAmountChangePacketHandler extends PacketHandlerBase<GoldAmountChangePacket>
{	
	private PlayState playState;

	public GoldAmountChangePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void doHandle(GoldAmountChangePacket packet)
	{
		System.out.println("Disabled");
		//playState.updateCharacterGold(packet.getNewGoldAmount());
	}

}
