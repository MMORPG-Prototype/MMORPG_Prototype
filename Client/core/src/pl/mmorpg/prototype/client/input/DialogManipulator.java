package pl.mmorpg.prototype.client.input;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;

public class DialogManipulator
{
	private Map<Integer, Table> mappedDialogs = new ConcurrentHashMap<>();
	private List<Table> dialogs = new LinkedList<>();
	private Table lastActiveDialog;

	public void map(Integer key, Table dialog)
	{
		mappedDialogs.put(key, dialog);
		lastActiveDialog = dialog;
	}

	public void add(Table dialog)
	{
		dialogs.add(dialog);
		lastActiveDialog = dialog;
	}

	public void showOrHide(Integer key)
	{
		Table dialogTarget = mappedDialogs.get(key);
		showOrHide(dialogTarget);
	}

	private void showOrHide(Table dialogTarget)
	{
		if (dialogTarget.isVisible())
			dialogTarget.setVisible(false);
		else
		{
			dialogTarget.setVisible(true);
			dialogTarget.toFront();
		}
	}

	public Collection<Table> getAllMapped()
	{
		return mappedDialogs.values();
	}

	public boolean isMapped(Integer key)
	{
		return mappedDialogs.containsKey(key);
	}

	private Table searchForDialog(Class<? extends Table> clazz)
	{
		return Stream.concat(dialogs.stream(), mappedDialogs.values().stream())
				.filter((d) -> d.getClass().equals(clazz))
				.findFirst()
				.orElseThrow(CannotFindSpecifiedDialogTypeException::new);
	}

	public void showOrHide(Class<? extends Table> clazz)
	{
		Table dialog = searchForDialog(clazz);
		showOrHide(dialog);
	}

	public void clear()
	{
		mappedDialogs.clear();
		dialogs.clear();
	}

	public void manageZIndexes()
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
		List<Table> mouseHoveringDialogs =
				Stream.concat(dialogs.stream(), mappedDialogs.values().stream())
				.filter((d) -> mouseHovers(d))
				.collect(Collectors.toList());
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
}
