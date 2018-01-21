package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.damage.NormalDamagePacket;

public class NormalDamagePacketHandler extends PacketHandlerAdapter<NormalDamagePacket>
{
    private PlayState playState;

    public NormalDamagePacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void handle(NormalDamagePacket packet)
    {
        playState.normalDamagePacketReceived(packet);
    }

    @Override
    public boolean canBeHandled(NormalDamagePacket packet)
    {
        return playState.isInitialized() && playState.has(((NormalDamagePacket) packet).getTargetId());
    }

}
