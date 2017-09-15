package pl.mmorpg.prototype.server.quests.observers;

import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.database.entities.Quest;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class RewardForFinishedQuestObserver implements QuestFinishedObserver
{
    private GameObjectsContainer gameObjectsContainer;
    private PacketsSender packetsSender;
    private GameDataRetriever gameDataRetriever;

    public RewardForFinishedQuestObserver(GameObjectsContainer gameObjectsContainer, PacketsSender packetSender, GameDataRetriever gameDataRetriever)
    {
        this.gameObjectsContainer = gameObjectsContainer;
        this.packetsSender = packetSender;
        this.gameDataRetriever = gameDataRetriever;
    }
    
    @Override
    public void playerFinishedQuest(int characterId, Quest quest)
    {
        PlayerCharacter playerCharacter = (PlayerCharacter) gameObjectsContainer.getObject(characterId);
        int connectionId = gameDataRetriever.getConnectionIdByCharacterId(characterId);
        packetsSender.sendTo(connectionId, PacketsMaker.makeQuestRewardPacket(quest));
        //TODO item rewards
        
        //Collection<ItemIdentifiers> itemsReward = quest.getItemsReward();
        //GameItemsFactory.produce(itemsReward, 1, IdSupplier.getId(), new InventoryPosition(-1, -1, -1));
    }

}
