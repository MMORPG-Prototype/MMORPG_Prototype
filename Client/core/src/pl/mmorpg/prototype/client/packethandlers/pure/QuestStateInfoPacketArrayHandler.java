package pl.mmorpg.prototype.client.packethandlers.pure;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerBase;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.quests.QuestCreator;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.clientservercommon.packets.QuestStateInfoPacket;

public class QuestStateInfoPacketArrayHandler extends PacketHandlerBase<QuestStateInfoPacket[]>
{
    private PlayState playState;

    public QuestStateInfoPacketArrayHandler(PlayState playState)
    {
        this.playState = playState;
    }

    @Override
    public void doHandle(QuestStateInfoPacket[] packets)
    {
        for (QuestStateInfoPacket packet : packets)
        {
            Quest quest = QuestCreator.create(packet);
            playState.questInfoReceived(quest);
        }
    }

}