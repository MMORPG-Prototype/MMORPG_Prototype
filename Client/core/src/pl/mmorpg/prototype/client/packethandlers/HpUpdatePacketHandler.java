package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.HpUpdatePacket;

public class HpUpdatePacketHandler extends PacketHandlerAdapter<HpUpdatePacket>
{
	private PlayState playState;

	public HpUpdatePacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(HpUpdatePacket packet)
	{
		playState.updateHp(packet.getId(), packet.getNewHp());
	}

}
