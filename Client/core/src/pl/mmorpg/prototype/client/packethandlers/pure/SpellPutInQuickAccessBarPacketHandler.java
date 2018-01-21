package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.SpellPutInQuickAccessBarPacket;

public class SpellPutInQuickAccessBarPacketHandler extends PacketHandlerBase<SpellPutInQuickAccessBarPacket>
{
	private PlayState playState;

	public SpellPutInQuickAccessBarPacketHandler(PlayState playState)
	{
		this.playState = playState;
	}
	
	@Override
	public void doHandle(SpellPutInQuickAccessBarPacket packet)
	{
		playState.putSpellInQuickAccessBarPacketReceived(packet);
	}

}
