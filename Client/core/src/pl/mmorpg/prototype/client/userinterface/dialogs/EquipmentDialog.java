package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ItemInventoryField;

public class EquipmentDialog extends Dialog
{

	public EquipmentDialog()
	{
		super("Equipment", Settings.DEFAULT_SKIN);
		int padding = 10;
		Table content = new Table(Settings.DEFAULT_SKIN);
		content.add().pad(padding);
		ItemInventoryField helmetField = new ItemInventoryField();
		helmetField.setHeight(45);
		helmetField.setWidth(45);
		content.add(helmetField).pad(padding);;
		content.add().pad(padding);;
		content.row();
		ItemInventoryField weaponField = new ItemInventoryField();
		weaponField.setHeight(110);
		weaponField.setWidth(45);
		content.add(weaponField).pad(padding);;
		ItemInventoryField armorField = new ItemInventoryField();
		armorField.setHeight(110);
		armorField.setWidth(55);
		content.add(armorField).pad(padding);;
		ItemInventoryField shieldField = new ItemInventoryField();
		shieldField.setHeight(45);
		shieldField.setWidth(45);
		content.add(shieldField).pad(padding);;
		content.row();
		content.add().pad(padding);
		ItemInventoryField bootsField = new ItemInventoryField();
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
