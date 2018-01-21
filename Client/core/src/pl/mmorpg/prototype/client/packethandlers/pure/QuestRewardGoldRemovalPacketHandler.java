package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.QuestRewardGoldRemovalPacket;

public class QuestRewardGoldRemovalPacketHandler extends PacketHandlerBase<QuestRewardGoldRemovalPacket>
{
    private PlayState playState;

    public QuestRewardGoldRemovalPacketHandler(PlayState playState)
    {
        this.playState = playState;
    }
    
    @Override
    public void doHandle(QuestRewardGoldRemovalPacket packet)
    {
        playState.questRewardGoldRemovalPacketReceived(packet);
    }

}