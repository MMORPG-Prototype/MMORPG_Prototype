package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.GoldReceivePacket;

public class GoldReceivePacketHandler extends PacketHandlerAdapter<GoldReceivePacket>
{
	private PlayState playState;
    
    public GoldReceivePacketHandler(PlayState playState)
    {
        this.playState = playState;
    }
	
	@Override
	public void handle(GoldReceivePacket packet)
	{
		playState.characterReceivedGold(packet.getGoldAmount());
	}

}
