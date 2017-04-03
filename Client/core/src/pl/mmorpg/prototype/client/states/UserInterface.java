package pl.mmorpg.prototype.client.states;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.input.DialogManipulator;
import pl.mmorpg.prototype.client.items.Item;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.states.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.states.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.client.states.dialogs.components.InventoryField;
import pl.mmorpg.prototype.client.states.helpers.InventoryManager;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class UserInterface
{
	private final Stage stage = Assets.getStage();
	public final MenuDialog menuDialog;
	public final InventoryDialog inventoryDialog;
	public final StatisticsDialog statisticsDialog;
	private final DialogManipulator dialogs = new DialogManipulator();

	private Item mouseHoldingItem = null;
	private Table lastActiveDialog;

	private PlayState linkedState;

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		this.linkedState = linkedState;
		menuDialog = new MenuDialog(this);
		inventoryDialog = new InventoryDialog(this);
		statisticsDialog = new StatisticsDialog(character);
		lastActiveDialog = inventoryDialog;

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
		manageDialogZIndexes();
	}

	private void manageDialogZIndexes()
	{
		List<Table> mouseHoveringDialogs = getDialogsOnMousePosition();
		if (!mouseHoveringDialogs.isEmpty() && !containsLastMouseHoveringDialog(mouseHoveringDialogs))
		{
			lastActiveDialog = mouseHoveringDialogs.iterator().next();
			lastActiveDialog.toFront();
		}
	}

	private List<Table> getDialogsOnMousePosition()
	{
		List<Table> mouseHoveringDialogs = new LinkedList<>();
		for (Table dialog : dialogs.getAll())
			if (mouseHovers(dialog))
				mouseHoveringDialogs.add(dialog);
		return mouseHoveringDialogs;
	}

	private boolean mouseHovers(Actor actor)
	{
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		return mouseX >= actor.getX() && mouseX <= actor.getRight() && mouseY >= actor.getY()
				&& mouseY <= actor.getTop();
	}


	private boolean containsLastMouseHoveringDialog(List<Table> mouseHoveringDialogs)
	{
		for (Table dialog : mouseHoveringDialogs)
			if (dialog == lastActiveDialog)
				return true;
		return false;
	}

	public DialogManipulator getDialogs()
	{
		return dialogs;
	}

	public void mapWithKeys()
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

	public void showOrHide(Class<? extends Table> dialogType)
	{
		dialogs.showOrHide(dialogType);
	}

	public void showDialogs()
	{
		stage.addActor(menuDialog);
		inventoryDialog.show(stage);
		statisticsDialog.show(stage);
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
}