package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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
		InventoryField helmetField = new InventoryField();
		helmetField.setHeight(45);
		helmetField.setWidth(45);
		content.add(helmetField).pad(padding);;
		content.add().pad(padding);;
		content.row();
		InventoryField weaponField = new InventoryField();
		weaponField.setHeight(110);
		weaponField.setWidth(45);
		content.add(weaponField).pad(padding);;
		InventoryField armorField = new InventoryField();
		armorField.setHeight(110);
		armorField.setWidth(55);
		content.add(armorField).pad(padding);;
		InventoryField shieldField = new InventoryField();
		shieldField.setHeight(45);
		shieldField.setWidth(45);
		content.add(shieldField).pad(padding);;
		content.row();
		content.add().pad(padding);
		InventoryField bootsField = new InventoryField();
		bootsField.setWidth(45);
		bootsField.setHeight(45);
		content.add(bootsField).pad(padding);
		content.add().pad(padding);
		
		this.getContentTable().add(content).fill();
		debugAll();
		content.setFillParent(true);
		setX(700);
		setWidth(260);
		setHeight(350);
		pack();
	}

}
