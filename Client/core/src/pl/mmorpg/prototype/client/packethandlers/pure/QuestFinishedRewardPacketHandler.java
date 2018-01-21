package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;

public class QuestFinishedRewardPacketHandler extends PacketHandlerBase<QuestFinishedRewardPacket>
{
    private PlayState playState;

    public QuestFinishedRewardPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void doHandle(QuestFinishedRewardPacket packet)
    {
        playState.questFinishedRewardPacketReceived(packet);
    }

}
