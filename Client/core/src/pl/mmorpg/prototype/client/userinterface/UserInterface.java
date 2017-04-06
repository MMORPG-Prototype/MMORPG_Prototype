package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.helpers.InventoryManager;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.ShortcutBarPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.components.InventoryField;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class UserInterface
{
	private final Stage stage = Assets.getStage();
	private final MenuDialog menuDialog;
	private final InventoryDialog inventoryDialog;
	private final StatisticsDialog statisticsDialog;
	private final ShortcutBarPane standardBarDialog;
	private final ActorManipulator dialogs = new ActorManipulator();

	private Item mouseHoldingItem = null;

	private PlayState linkedState;

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		this.linkedState = linkedState;
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this);
		statisticsDialog = new StatisticsDialog(character);
		standardBarDialog = new ShortcutBarPane(this);
		mapDialogsWithKeys();
		addOtherDialogs();
		showDialogs();
	}

	private void addOtherDialogs()
	{
		dialogs.add(standardBarDialog);
	}
	
	public void showDialogs()
	{
		stage.addActor(standardBarDialog);
		stage.addActor(menuDialog);
		inventoryDialog.show(stage);
		statisticsDialog.show(stage);
	}

	public void draw(SpriteBatch batch)
	{
		stage.draw();
		batch.begin();
		if (mouseHoldingItem != null)
			mouseHoldingItem.renderWhenDragged(batch);
		batch.end();
	}

	public void update()
	{
		stage.act();
		dialogs.manageZIndexes();
	}

	
	public ActorManipulator getDialogs()
	{
		return dialogs;
	}

	public void mapDialogsWithKeys()
	{
		dialogs.map(Keys.M, menuDialog);
		dialogs.map(Keys.I, inventoryDialog);
		dialogs.map(Keys.C, statisticsDialog);
	}

	public void clear()
	{
		stage.clear();
		dialogs.clear();
	}

	public InputProcessor getStage()
	{
		return stage;
	}

	public void inventoryFieldClicked(InventoryField inventoryField)
	{
		mouseHoldingItem = InventoryManager.inventoryFieldClicked(mouseHoldingItem, inventoryField);
	}

	public void userWantsToDisconnect()
	{
		linkedState.userWantsToDisconnect();
	}

	public void showOrHideDialog(Class<? extends Table> clazz)
	{
		dialogs.showOrHide(clazz);
	}

	public void userWantsToChangeCharacter()
	{
		linkedState.userWantsToChangeCharacter();
	}

	public void addItemToInventory(Item newItem)
	{
		inventoryDialog.addItem(newItem);
	}
}