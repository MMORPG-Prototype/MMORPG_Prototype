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

import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;

public class ActorManipulator
{
	private Map<Integer, Actor> mappedDialogs = new ConcurrentHashMap<>();
	private List<Actor> dialogs = new LinkedList<>();
	private Actor lastActiveDialog;

	public void map(Integer key, Actor dialog)
	{
		mappedDialogs.put(key, dialog);
		lastActiveDialog = dialog;
	}

	public void add(Actor dialog)
	{
		dialogs.add(dialog);
		lastActiveDialog = dialog;
	}

	public void showOrHide(Integer key)
	{
		Actor dialogTarget = mappedDialogs.get(key);
		showOrHide(dialogTarget);
	}

	private void showOrHide(Actor dialogTarget)
	{
		if (dialogTarget.isVisible())
			dialogTarget.setVisible(false);
		else
		{
			dialogTarget.setVisible(true);
			dialogTarget.toFront();
		}
	}
	
	public void hideKeyMappedDialogs()
	{
		for(Actor dialog : mappedDialogs.values())
			dialog.setVisible(false);
	}

	public Collection<Actor> getAllMapped()
	{
		return mappedDialogs.values();
	}

	public boolean isMapped(Integer key)
	{
		return mappedDialogs.containsKey(key);
	}

	public Actor searchForDialog(Class<? extends Actor> clazz)
	{
		return Stream.concat(dialogs.stream(), mappedDialogs.values().stream())
				.filter((d) -> d.getClass().equals(clazz))
				.findFirst()
				.orElseThrow(CannotFindSpecifiedDialogTypeException::new);
	}

	public void showOrHide(Class<? extends Actor> clazz)
	{
		Actor dialog = searchForDialog(clazz);
		showOrHide(dialog);
	}

	public void clear()
	{
		mappedDialogs.clear();
		dialogs.clear();
	}

	public void manageZIndexes()
	{
		List<Actor> mouseHoveringDialogs = getDialogsOnMousePosition();
		if (!mouseHoveringDialogs.isEmpty())
		{
			if(!containsLastMouseHoveringDialog(mouseHoveringDialogs))
			{
				lastActiveDialog = mouseHoveringDialogs.iterator().next();
				lastActiveDialog.toFront();
			}
			else
				lastActiveDialog.toFront();
		}
	}

	private List<Actor> getDialogsOnMousePosition()
	{
		List<Actor> mouseHoveringDialogs =
				Stream.concat(dialogs.stream(), mappedDialogs.values().stream())
				.filter((d) -> d.isVisible() && mouseHovers(d))
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

	private boolean containsLastMouseHoveringDialog(List<Actor> mouseHoveringDialogs)
	{
		for (Actor dialog : mouseHoveringDialogs)
			if (dialog == lastActiveDialog)
				return true;
		return false;
	}
}
