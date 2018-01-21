package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestAcceptedPacket;

public class QuestAcceptedPacketHandler extends PacketHandlerAdapter<QuestAcceptedPacket>
{
    private final PlayState playState;
    
    public QuestAcceptedPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void handle(QuestAcceptedPacket packet)
    {
        playState.questAcceptedPacketReceived(packet);
    }

}
