package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.ButtonCreator;

public class ShortcutBarDialog extends Dialog
{
	public ShortcutBarDialog(UserInterface linkedInterface)
	{
		super("", Settings.DEFAULT_SKIN);

		TextButton menuButton = ButtonCreator.createTextButton("Menu", 
				() -> linkedInterface.showOrHideDialog(MenuDialog.class));
		TextButton inventoryButton = ButtonCreator.createTextButton("Inventory", 
				() -> linkedInterface.showOrHideDialog(InventoryDialog.class));;
		TextButton statisticsButton = ButtonCreator.createTextButton("Statistics", 
				() -> linkedInterface.showOrHideDialog(StatisticsDialog.class));
		TextButton chatButton = ButtonCreator.createTextButton("Chat", 
				() -> linkedInterface.showOrHideDialog(ChatDialog.class));
		TextButton equipmentButton = ButtonCreator.createTextButton("Equipment", 
				() -> linkedInterface.showOrHideDialog(EquipmentDialog.class));
		TextButton consoleDialog = ButtonCreator.createTextButton("Console", 
				() -> linkedInterface.showOrHideDialog(ConsoleDialog.class));		

		add(consoleDialog).bottom().right();
		add(chatButton).bottom().right();
		add(statisticsButton).bottom().right();
		add(inventoryButton).bottom().right();
		add(equipmentButton).bottom().right();
		add(menuButton).bottom().right();
	}
}
