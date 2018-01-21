package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;

public class SpellPutInQuickAccessBarPacketHandler extends PacketHandlerAdapter<SpellPutInQuickAccessBarPacket>
{
	private PlayState playState;

	public SpellPutInQuickAccessBarPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void handle(SpellPutInQuickAccessBarPacket packet)
	{
		playState.putSpellInQuickAccessBarPacketReceived(packet);
	}

}
