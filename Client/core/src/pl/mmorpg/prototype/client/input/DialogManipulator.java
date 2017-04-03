package pl.mmorpg.prototype.client.input;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;

public class DialogManipulator
{
	private Map<Integer, Table> dialogs = new ConcurrentHashMap<>();

	public void map(Integer key, Table dialog)
	{
		dialogs.put(key, dialog);
	}

	public void showOrHide(Integer key)
	{
		Table dialogTarget = dialogs.get(key);
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

	public Collection<Table> getAll()
	{
		return dialogs.values();
	}

	public boolean isMapped(Integer key)
	{
		return dialogs.containsKey(key);
	}

	private Table searchForDialog(Class<? extends Table> clazz)
	{
		for (Table dialog : dialogs.values())
			if (dialog.getClass().equals(clazz))
				return dialog;
		throw new CannotFindSpecifiedDialogTypeException();
	}

	public void showOrHide(Class<? extends Table> clazz)
	{
		Table dialog = searchForDialog(clazz);
		showOrHide(dialog);
	}

	public void clear()
	{
		dialogs.clear();
	}
}
