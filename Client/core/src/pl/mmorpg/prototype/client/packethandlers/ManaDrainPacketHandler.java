package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.ManaDrainPacket;

public class ManaDrainPacketHandler extends PacketHandlerBase<ManaDrainPacket>
{

    private PlayState playState;

    public ManaDrainPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }
    
    @Override
    public void handlePacket(ManaDrainPacket packet)
    {
        playState.playerUsedMana(packet.manaDrained);
    }
    
}
