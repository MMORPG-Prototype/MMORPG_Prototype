package pl.mmorpg.prototype.client.packethandlers;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import pl.mmorpg.prototype.client.quests.Progress;
import pl.mmorpg.prototype.client.quests.Quest;
import pl.mmorpg.prototype.client.quests.QuestTask;
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
    public void handlePacket(QuestStateInfoPacket[] packets)
    {
        for (QuestStateInfoPacket packet : packets)
        {
            Collection<QuestTask> questTasks = Arrays.stream(packet.getQuestTasks())
                    .map(questTask -> new QuestTask(questTask.getDescription(), questTask.getPercentFinished()))
                    .collect(Collectors.toList());
            Progress progress = new Progress(questTasks);
            Quest quest = new Quest(progress, packet.getQuestName(), packet.getDescription());
            playState.questInfoReceived(quest);
        }
    }

}
