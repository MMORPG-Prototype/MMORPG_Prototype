package pl.mmorpg.prototype.server.packetshandling.characteractions;

import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang3.NotImplementedException;

import com.esotericsoftware.kryonet.Connection;

import pl.mmorpg.prototype.clientservercommon.ItemIdentifiers;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveItemRewardPacket;
import pl.mmorpg.prototype.server.communication.IdSupplier;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.database.entities.CharactersQuestsItemReward;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;
import pl.mmorpg.prototype.server.database.entities.components.InventoryPosition;
import pl.mmorpg.prototype.server.database.entities.jointables.CharactersQuests;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.items.GameItemsFactory;
import pl.mmorpg.prototype.server.objects.items.Item;
import pl.mmorpg.prototype.server.packetshandling.GameDataRetriever;
import pl.mmorpg.prototype.server.packetshandling.PacketHandlerBase;
import pl.mmorpg.prototype.server.quests.events.Event;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public class RetrieveItemRewardPacketHandler extends PacketHandlerBase<RetrieveItemRewardPacket>
{
    private GameDataRetriever gameDataRetreiver;
    private GameObjectsContainer gameContainer;

    public RetrieveItemRewardPacketHandler(GameDataRetriever gameDataRetriever, GameObjectsContainer gameContainer)
    {
        this.gameDataRetreiver = gameDataRetriever;
        this.gameContainer = gameContainer;
    }

    @Override
    public Collection<Event> handle(Connection connection, RetrieveItemRewardPacket packet)
    {
        UserCharacter character = gameDataRetreiver.getUserCharacterByConnectionId(connection.getID());
        CharactersQuests quest = findSuiteQuest(character.getQuests(), packet.getQuestName());
        CharactersQuestsItemReward itemReward = findSuiteItemReward(quest.getItemsReward(), packet.getItemIdentifier());
        PlayerCharacter player = (PlayerCharacter) gameContainer.getObject(character.getId());
        InventoryPosition inventoryPosition = new InventoryPosition(packet.getDesiredInventoryPage(),
                packet.getDesiredInventoryX(), packet.getDesiredInventoryY());
        Item gameItem;
        if (itemReward.getNumberOfItems() == packet.getNumberOfItems())
            gameItem = GameItemsFactory.produce(itemReward.getItemIdentifier(), itemReward.getNumberOfItems(),
                    IdSupplier.getId(), inventoryPosition);
        else
            throw new NotImplementedException("Not implemented yet");

        player.addItem(gameItem);
        connection.sendTCP(PacketsMaker.makeItemRewardRemovePacket(packet.getItemIdentifier(),
                itemReward.getNumberOfItems(), packet.getRetrieveItemDialogId()));
        connection.sendTCP(PacketsMaker.makeItemPacket(gameItem));
        return Collections.emptyList();
    }

    private CharactersQuests findSuiteQuest(Collection<CharactersQuests> quests, String questName)
    {
        return quests.stream().filter(characterQuest -> characterQuest.getQuest().getName().equals(questName)).findAny()
                .get();
    }

    private CharactersQuestsItemReward findSuiteItemReward(Collection<CharactersQuestsItemReward> itemReward,
            String itemIdentifier)
    {
        return itemReward.stream()
                .filter(reward -> ItemIdentifiers.valueOf(itemIdentifier).equals(reward.getItemIdentifier())).findAny()
                .get();
    }
}
