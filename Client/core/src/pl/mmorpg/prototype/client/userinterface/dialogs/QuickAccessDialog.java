package pl.mmorpg.prototype.client.userinterface.dialogs;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.exceptions.CannotUseThisItemException;
import pl.mmorpg.prototype.client.exceptions.NoSuchItemInQuickAccessBarException;
import pl.mmorpg.prototype.client.items.ItemIcon;
import pl.mmorpg.prototype.client.items.ItemUseable;
import pl.mmorpg.prototype.client.objects.monsters.Monster;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.QuickAccessIcon;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.InventoryTextField;

public class QuickAccessDialog extends Dialog
{
    private Map<Integer, ButtonField<QuickAccessIcon>> quickAccessButtons = new TreeMap<>();
    private UserInterface linkedInterface;

    public QuickAccessDialog(UserInterface linkedInterface)
    {
        super("Quick access", Settings.DEFAULT_SKIN);
        this.linkedInterface = linkedInterface;

        HorizontalGroup buttons = new HorizontalGroup().padBottom(8).space(4).padTop(0).fill();
        for (int i = 0; i < 12; i++)
        {
            ButtonField<QuickAccessIcon> field = createField(i);
            quickAccessButtons.put(i, field);
            buttons.addActor(field);
        }
        add(buttons);
        row();
        pack();
        this.setHeight(80);
        this.setX(430);
        this.setMovable(false);
    }

    private ButtonField<QuickAccessIcon> createField(int cellPosition)
    {
        InventoryTextField<QuickAccessIcon> inventoryField = new InventoryTextField<>(
                "F" + String.valueOf(cellPosition + 1));
        inventoryField.setTextShiftX(-16);
        inventoryField.setTextShiftY(-4);
        inventoryField.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                fieldClicked(cellPosition);
            }
        });
        return inventoryField;
    }

    private void fieldClicked(int cellPosition)
    {
        linkedInterface.quickAccessButtonClicked(quickAccessButtons.get(cellPosition), cellPosition);
    }

    public void useButtonItem(int cellPosition, Monster target, PacketsSender packetSender)
    {
        ButtonField<QuickAccessIcon> fieldWithIcon = quickAccessButtons.get(cellPosition);
        if (fieldWithIcon.hasContent())
        {
            QuickAccessIcon quickAccessIcon = fieldWithIcon.getContent();
            String itemIdentifier = quickAccessIcon.getItemIdenfier();
            ItemIcon item = linkedInterface.searchForItem(itemIdentifier);
            if (item == null)
                return;
            if (!(item instanceof ItemUseable))
                throw new CannotUseThisItemException((ItemIcon) item);

            ((ItemUseable) item).use(target, packetSender);
        }

    }

    public boolean hasItem(QuickAccessIcon item)
    {
        for (ButtonField<QuickAccessIcon> field : quickAccessButtons.values())
            if (field.hasContent() && field.getContent() == item)
                return true;
        return false;
    }

    public void removeItem(QuickAccessIcon usedItem)
    {
        for (ButtonField<QuickAccessIcon> field : quickAccessButtons.values())
            if (field.hasContent() && field.getContent() == usedItem)
            {
                field.removeContent();
                return;
            }
        throw new NoSuchItemInQuickAccessBarException(usedItem);
    }

    public void decreaseNumberOfItems(String identifier)
    {
        getValidIcons(identifier).forEach(QuickAccessIcon::decreaseItemNumber);
    }

    private Stream<QuickAccessIcon> getValidIcons(String identifier)
    {
        return quickAccessButtons.values().stream().map(ButtonField::getContent).filter(Objects::nonNull)
                .filter(icon -> icon.getItemIdenfier().equals(identifier));
    }

    public void increaseNumbers(String identifier, int itemCount)
    {
        getValidIcons(identifier).forEach(icon -> icon.increaseItemNumber(itemCount));
    }

    public boolean isFieldTaken(int cellPosition)
    {
        return quickAccessButtons.get(cellPosition).hasChildren();
    }

    public void putItem(String itemIdentifier, int cellPosition, ItemCounter itemCounter)
    {
        QuickAccessIcon icon = new QuickAccessIcon(itemIdentifier, itemCounter);
        ButtonField<QuickAccessIcon> quickAccessField = quickAccessButtons.get(cellPosition);
        quickAccessField.put(icon);
    }

}
