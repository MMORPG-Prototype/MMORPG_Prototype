package pl.mmorpg.prototype.client.states;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.input.DialogManipulator;
import pl.mmorpg.prototype.client.states.dialogs.InventoryDialog;
import pl.mmorpg.prototype.client.states.dialogs.MenuDialog;
import pl.mmorpg.prototype.client.states.dialogs.StatisticsDialog;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class UserInterface
{
	private Table lastActiveDialog;

	public final MenuDialog menuDialog;
	public final InventoryDialog inventoryDialog;
	public StatisticsDialog statisticsDialog;
	private final DialogManipulator dialogs = new DialogManipulator();

	public UserInterface(PlayState linkedState, UserCharacterDataPacket character)
	{
		menuDialog = new MenuDialog(linkedState);
		inventoryDialog = new InventoryDialog(linkedState);
		statisticsDialog = new StatisticsDialog(character);
		lastActiveDialog = inventoryDialog;

	}

	public void update()
	{
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
		dialogs.clear();

	}

	public void showOrHide(Class<? extends Table> dialogType)
	{
		dialogs.showOrHide(dialogType);
	}
}