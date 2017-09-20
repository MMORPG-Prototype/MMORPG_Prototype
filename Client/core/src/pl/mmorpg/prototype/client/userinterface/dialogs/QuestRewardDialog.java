package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemPositionSupplier;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.QuestRewardIcon;
import pl.mmorpg.prototype.clientservercommon.packets.ItemRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveItemRewardPacket;

public class QuestRewardDialog extends AutoCleanupOnCloseButtonDialog
{
    private Map<String, InventoryField<QuestRewardIcon>> rewardFields = new HashMap<>();

    public QuestRewardDialog(ActorManipulator linkedManipulator, long id, QuestFinishedRewardPacket questReward,
            ItemPositionSupplier desiredItemPositionSupplier, PacketsSender packetsSender)
    {
        super(questReward.getQuestName(), linkedManipulator, id);

        for (ItemRewardPacket itemReward : questReward.getItemReward())
        {
            InventoryField<QuestRewardIcon> rewardField = createRewardField(desiredItemPositionSupplier, packetsSender,
                    questReward.getQuestName());
            QuestRewardIcon questRewardIcon = new QuestRewardIcon(itemReward.getItemIdentifier(),
                    itemReward.getNumberOfItems());
            rewardField.put(questRewardIcon);
            rewardFields.put(itemReward.getItemIdentifier(), rewardField);
            this.add(rewardField);
        }

        this.pack();
    }

    private InventoryField<QuestRewardIcon> createRewardField(ItemPositionSupplier desiredItemPositionSupplier,
            PacketsSender packetsSender, String questName)
    {
        InventoryField<QuestRewardIcon> rewardField = new InventoryField<>();
        rewardField.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                rewardFieldButtonClicked(rewardField, desiredItemPositionSupplier, packetsSender, questName);
            }
        });
        return rewardField;
    }

    private void rewardFieldButtonClicked(InventoryField<QuestRewardIcon> rewardField,
            ItemPositionSupplier desiredItemPositionSupplier, PacketsSender packetsSender, String questName)
    {
        if (rewardField.hasContent())
        {
            QuestRewardIcon questRewardIcon = rewardField.getContent();
            String itemIdentifier = questRewardIcon.getItemIdentifier();
            int numberOfItems = questRewardIcon.getNumberOfItems();
            ItemInventoryPosition desiredItemPosition = desiredItemPositionSupplier.get(itemIdentifier, numberOfItems);
            RetrieveItemRewardPacket retrieveItemRewardPacket = PacketsMaker.makeRetrieveItemRewardPacket(
                    itemIdentifier, numberOfItems, desiredItemPosition, getId(), questName);
            packetsSender.send(retrieveItemRewardPacket);
        }
    }

    public void removeItem(String itemIdentifier, int numberOfItems)
    {
        InventoryField<QuestRewardIcon> rewardField = rewardFields.get(itemIdentifier);
        QuestRewardIcon questReward = rewardField.getContent();
        questReward.decreaseNumberOfItems(numberOfItems);
        if (questReward.getNumberOfItems() == 0)
            rewardField.removeContent();
    }
}
