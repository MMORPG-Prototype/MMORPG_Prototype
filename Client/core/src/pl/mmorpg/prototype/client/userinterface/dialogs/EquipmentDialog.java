package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import pl.mmorpg.prototype.client.exceptions.GameException;
import pl.mmorpg.prototype.client.items.equipment.EquipmentItem;
import pl.mmorpg.prototype.client.objects.icons.items.Item;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.inventory.ButtonField;
import pl.mmorpg.prototype.clientservercommon.EquipmentPosition;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EquipmentDialog extends Dialog
{
	private final Map<EquipmentPosition, ButtonField<EquipmentItem>> equipmentFields = new HashMap<>();

	public EquipmentDialog(UserInterface linkedInterface)
	{
		super("Equipment", Settings.DEFAULT_SKIN);
		int padding = 10;
		Table content = new Table(Settings.DEFAULT_SKIN);
		content.add().pad(padding);
		ButtonField<EquipmentItem> headField = ButtonCreator.createButtonField(() -> linkedInterface.equipmentFieldClicked(EquipmentPosition.HEAD));
		headField.setHeight(45);
		headField.setWidth(45);
		content.add(headField).pad(padding);
		content.add().pad(padding);
		content.row();
		ButtonField<EquipmentItem> rightHandField = ButtonCreator.createButtonField(() -> linkedInterface.equipmentFieldClicked(EquipmentPosition.RIGHT_HAND));
		rightHandField.setHeight(110);
		rightHandField.setWidth(45);
		content.add(rightHandField).pad(padding);
		ButtonField<EquipmentItem> chestField = ButtonCreator.createButtonField(() -> linkedInterface.equipmentFieldClicked(EquipmentPosition.CHEST));
		chestField.setHeight(110);
		chestField.setWidth(55);
		content.add(chestField).pad(padding);
		ButtonField<EquipmentItem> leftHandField = ButtonCreator.createButtonField(() -> linkedInterface.equipmentFieldClicked(EquipmentPosition.LEFT_HAND));
		leftHandField.setHeight(45);
		leftHandField.setWidth(45);
		content.add(leftHandField).pad(padding);
		content.row();
		content.add().pad(padding);
		ButtonField<EquipmentItem> feetField = ButtonCreator.createButtonField(() -> linkedInterface.equipmentFieldClicked(EquipmentPosition.FEET));
		feetField.setWidth(45);
		feetField.setHeight(45);
		content.add(feetField).pad(padding);
		content.add().pad(padding);

		equipmentFields.put(EquipmentPosition.HEAD, headField);
		equipmentFields.put(EquipmentPosition.RIGHT_HAND, rightHandField);
		equipmentFields.put(EquipmentPosition.LEFT_HAND, leftHandField);
		equipmentFields.put(EquipmentPosition.CHEST, chestField);
		equipmentFields.put(EquipmentPosition.FEET, feetField);

		this.getContentTable().add(content).center().padLeft(-10).fill();
		setX(1147);
		setY(285);
		setWidth(253);
		setHeight(310);
	}

	public ButtonField<EquipmentItem> getItemField(EquipmentPosition equipmentPosition)
	{
		return equipmentFields.get(equipmentPosition);
	}

	public void equipItem(EquipmentPosition equipmentPosition, EquipmentItem equipmentItem)
	{
		getItemField(equipmentPosition).put(equipmentItem);
	}

	public EquipmentItem takeOffItem(EquipmentPosition equipmentPosition)
	{
		EquipmentItem itemToRemove = getItemField(equipmentPosition).getContent();
		getItemField(equipmentPosition).removeContent();
		return itemToRemove;
	}

	public boolean isItemEquipped(EquipmentItem item)
	{
		if (item == null)
			throw new GameException("This should not happen");
		return equipmentFields.values().stream()
				.map(ButtonField::getContent)
				.filter(Objects::nonNull)
				.map(Item::getId)
				.anyMatch(id -> id == item.getId());
	}
}
