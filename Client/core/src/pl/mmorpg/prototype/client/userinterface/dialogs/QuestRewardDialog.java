package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsMaker;
import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.ItemInventoryPosition;
import pl.mmorpg.prototype.client.items.ItemPositionSupplier;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.AutoCleanupOnCloseButtonDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.StringValueLabel;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.quest.QuestRewardIcon;
import pl.mmorpg.prototype.clientservercommon.packets.ItemRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.quest.QuestFinishedRewardPacket;
import pl.mmorpg.prototype.clientservercommon.packets.playeractions.RetrieveItemRewardPacket;

public class QuestRewardDialog extends AutoCleanupOnCloseButtonDialog
{
    private final Map<String, ButtonField<QuestRewardIcon>> rewardFields = new HashMap<>();
    private final StringValueLabel<Integer> goldLabel;

    public QuestRewardDialog(ActorManipulator linkedManipulator, long id, QuestFinishedRewardPacket questReward,
            ItemPositionSupplier desiredItemPositionSupplier, PacketsSender packetsSender)
    {
        super(questReward.getQuestName(), linkedManipulator, id);

        for (ItemRewardPacket itemReward : questReward.getItemReward())
        {
            ButtonField<QuestRewardIcon> rewardField = createRewardField(desiredItemPositionSupplier, packetsSender,
                    questReward.getQuestName());
            QuestRewardIcon questRewardIcon = new QuestRewardIcon(itemReward.getItemIdentifier(),
                    itemReward.getNumberOfItems());
            rewardField.put(questRewardIcon);
            rewardFields.put(itemReward.getItemIdentifier(), rewardField);
            this.add(rewardField);
        }
        this.row();
        goldLabel = new StringValueLabel<>("Gold: ", Settings.DEFAULT_SKIN,
                questReward.getGoldReward());
        Button takeGoldButton = ButtonCreator.createTextButton("Take",
                () -> packetsSender.send(PacketsMaker.makeRetrieveGoldRewardPacket(questReward.getQuestName())));
        this.add(goldLabel);
        this.add(takeGoldButton);
        this.pack();
    }

    private ButtonField<QuestRewardIcon> createRewardField(ItemPositionSupplier desiredItemPositionSupplier,
            PacketsSender packetsSender, String questName)
    {
        ButtonField<QuestRewardIcon> rewardField = new ButtonField<>();
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

    private void rewardFieldButtonClicked(ButtonField<QuestRewardIcon> rewardField,
            ItemPositionSupplier desiredItemPositionSupplier, PacketsSender packetsSender, String questName)
    {
        if (rewardField.hasContent())
        {
            QuestRewardIcon questRewardIcon = rewardField.getContent();
            String itemIdentifier = questRewardIcon.getItemIdentifier();
            int numberOfItems = questRewardIcon.getNumberOfItems();
            ItemInventoryPosition desiredItemPosition = desiredItemPositionSupplier.get(itemIdentifier, numberOfItems);
            RetrieveItemRewardPacket retrieveItemRewardPacket = PacketsMaker.makeRetrieveItemRewardPacket(
                    itemIdentifier, numberOfItems, desiredItemPosition, questName);
            packetsSender.send(retrieveItemRewardPacket);
        }
    }

    public void removeItem(String itemIdentifier, int numberOfItems)
    {
        ButtonField<QuestRewardIcon> rewardField = rewardFields.get(itemIdentifier);
        QuestRewardIcon questReward = rewardField.getContent();
        questReward.decreaseNumberOfItems(numberOfItems);
        if (questReward.getNumberOfItems() == 0)
            rewardField.removeContent();
    }

    public void updateGoldByDecreasingBy(int goldAmount)
    {
        goldLabel.setValue(goldLabel.getValue() - goldAmount);
    }
}
