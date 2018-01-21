package pl.mmorpg.prototype.client.packethandlers;

import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;

public class QuestFinishedRewardPacketHandler extends PacketHandlerAdapter<QuestFinishedRewardPacket>
{
    private PlayState playState;

    public QuestFinishedRewardPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void handle(QuestFinishedRewardPacket packet)
    {
        playState.questFinishedRewardPacketReceived(packet);
    }

}
