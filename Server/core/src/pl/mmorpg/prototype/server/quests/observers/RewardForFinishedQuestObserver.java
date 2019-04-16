package pl.mmorpg.prototype.server.quests.observers;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.data.entities.Quest;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class RewardForFinishedQuestObserver implements QuestFinishedObserver
{
    private PacketsSender packetsSender;
    private GameObjectsContainer gameObjectsContainer;

    public RewardForFinishedQuestObserver(PacketsSender packetSender, GameObjectsContainer gameObjectsContainer)
    {
        this.packetsSender = packetSender;
        this.gameObjectsContainer = gameObjectsContainer;
    }
    
    @Override
    public void playerFinishedQuest(int characterId, Quest quest)
    {
        PlayerCharacter character = (PlayerCharacter) gameObjectsContainer.getObject(characterId);
        int connectionId = character.getConnectionId();
        packetsSender.sendTo(connectionId, PacketsMaker.makeQuestFinishedRewardPacket(quest));
    }

}
