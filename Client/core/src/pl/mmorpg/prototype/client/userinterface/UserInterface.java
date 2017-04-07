package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.exceptions.NotImplementedException;
import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.helpers.InventoryManager;
import pl.mmorpg.prototype.client.userinterface.dialogs.EquipmentDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.HitPointManaPointPane;
import pl.mmorpg.prototype.client.userinterface.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.userinterface.dialogs.QuickAccessDialog;
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
	private final HitPointManaPointPane hpMpDialog;
	private final QuickAccessDialog quickAccessDialog;
	private final EquipmentDialog equipmentDialog;
	private final ActorManipulator dialogs = new ActorManipulator();

	private Item mouseHoldingItem = null;

	private PlayState linkedState;

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		this.linkedState = linkedState;
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this, character.getGold());
		statisticsDialog = new StatisticsDialog(character);
		standardBarDialog = new ShortcutBarPane(this);
		hpMpDialog = new HitPointManaPointPane(character);
		quickAccessDialog = new QuickAccessDialog(this);
		equipmentDialog = new EquipmentDialog();
		
		mapDialogsWithKeys();
		addOtherDialogs();
		showDialogs();
	}

	private void addOtherDialogs()
	{
		dialogs.add(standardBarDialog);
		dialogs.add(hpMpDialog);
		dialogs.add(quickAccessDialog);
	}
	
	public void showDialogs()
	{
		stage.addActor(quickAccessDialog);
		stage.addActor(hpMpDialog);
		stage.addActor(standardBarDialog);
		stage.addActor(menuDialog);
		stage.addActor(inventoryDialog);
		stage.addActor(statisticsDialog);
		stage.addActor(equipmentDialog);
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
		dialogs.map(Keys.E, equipmentDialog);
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

	public void quickAccesButtonClicked(int cellPosition)
	{
		throw new NotImplementedException();		
	}
}