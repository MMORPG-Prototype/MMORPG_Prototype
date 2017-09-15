package pl.mmorpg.prototype.client.packethandlers;

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
    public void handlePacket(QuestFinishedRewardPacket packet)
    {
        int debug = 0;
        debug++;
    }

}
