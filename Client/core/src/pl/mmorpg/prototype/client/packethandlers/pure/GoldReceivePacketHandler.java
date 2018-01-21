package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;

public class GoldReceivePacketHandler extends PacketHandlerBase<GoldReceivePacket>
{
	private PlayState playState;
    
    public GoldReceivePacketHandler(PlayState playState)
    {
        this.playState = playState;
    }
	
	@Override
	public void doHandle(GoldReceivePacket packet)
	{
		playState.characterReceivedGold(packet.getGoldAmount());
	}

}