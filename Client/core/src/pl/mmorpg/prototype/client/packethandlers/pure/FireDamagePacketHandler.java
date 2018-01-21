package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.damage.FireDamagePacket;

public class FireDamagePacketHandler extends PacketHandlerBase<FireDamagePacket>
{
    private PlayState playState;
    
    public FireDamagePacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void doHandle(FireDamagePacket packet)
    {
        playState.fireDamagePacketReceived(packet);
    }

}
