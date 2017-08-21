package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;

public class EquipmentDialog extends Dialog
{

	public EquipmentDialog()
	{
		super("Equipment", Settings.DEFAULT_SKIN);
		int padding = 10;
		Table content = new Table(Settings.DEFAULT_SKIN);
		content.add().pad(padding);
		InventoryField<Item> helmetField = new InventoryField<Item>();
		helmetField.setHeight(45);
		helmetField.setWidth(45);
		content.add(helmetField).pad(padding);;
		content.add().pad(padding);;
		content.row();
		InventoryField<Item> weaponField = new InventoryField<Item>();
		weaponField.setHeight(110);
		weaponField.setWidth(45);
		content.add(weaponField).pad(padding);;
		InventoryField<Item> armorField = new InventoryField<Item>();
		armorField.setHeight(110);
		armorField.setWidth(55);
		content.add(armorField).pad(padding);;
		InventoryField<Item> shieldField = new InventoryField<Item>();
		shieldField.setHeight(45);
		shieldField.setWidth(45);
		content.add(shieldField).pad(padding);;
		content.row();
		content.add().pad(padding);
		InventoryField<Item> bootsField = new InventoryField<Item>();
		bootsField.setWidth(45);
		bootsField.setHeight(45);
		content.add(bootsField).pad(padding);
		content.add().pad(padding);
		
		this.getContentTable().add(content).center().padLeft(-10).fill();
		setX(1147);
		setY(285);
		setWidth(253);
		setHeight(310);
	}

}
