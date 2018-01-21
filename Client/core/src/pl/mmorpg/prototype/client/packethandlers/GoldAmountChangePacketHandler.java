package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.GoldAmountChangePacket;

public class GoldAmountChangePacketHandler extends PacketHandlerAdapter<GoldAmountChangePacket>
{	
	private PlayState playState;

	public GoldAmountChangePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}

	@Override
	public void handle(GoldAmountChangePacket packet)
	{
		playState.updateCharacterGold(packet.getNewGoldAmount());
	}

}
