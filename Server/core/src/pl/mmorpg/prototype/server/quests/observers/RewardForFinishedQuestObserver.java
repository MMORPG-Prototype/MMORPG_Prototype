package pl.mmorpg.prototype.server.quests.observers;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;

public class RewardForFinishedQuestObserver implements QuestFinishedObserver
{
    private PacketsSender packetsSender;
    private GameDataRetriever gameDataRetriever;

    public RewardForFinishedQuestObserver(PacketsSender packetSender, GameDataRetriever gameDataRetriever)
    {
        this.packetsSender = packetSender;
        this.gameDataRetriever = gameDataRetriever;
    }
    
    @Override
    public void playerFinishedQuest(int characterId, Quest quest)
    {
        int connectionId = gameDataRetriever.getConnectionIdByCharacterId(characterId);
        packetsSender.sendTo(connectionId, PacketsMaker.makeQuestFinishedRewardPacket(quest));
    }

}
