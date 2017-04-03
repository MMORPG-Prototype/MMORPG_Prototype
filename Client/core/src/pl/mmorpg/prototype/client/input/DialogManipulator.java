package pl.mmorpg.prototype.client.input;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pl.mmorpg.prototype.client.exceptions.CannotFindSpecifiedDialogTypeException;
import pl.mmorpg.prototype.client.states.dialogs.CustomDialog;
import pl.mmorpg.prototype.client.states.dialogs.CustomDialogs;

public class DialogManipulator
{
	private Map<Integer, CustomDialog> dialogs = new ConcurrentHashMap<>();

	public void map(Integer key, CustomDialog dialog)
	{
		dialogs.put(key, dialog);
	}

	public void showOrHide(Integer key)
	{
		CustomDialog dialogTarget = dialogs.get(key);
		showOrHide(dialogTarget);
	}

	private void showOrHide(CustomDialog dialogTarget)
	{
		if (dialogTarget.isVisible())
			dialogTarget.setVisible(false);
		else
		{
			dialogTarget.setVisible(true);
			dialogTarget.toFront();
		}
	}

	public Collection<CustomDialog> getAll()
	{
		return dialogs.values();
	}

	public boolean isMapped(Integer key)
	{
		return dialogs.containsKey(key);
	}

	private CustomDialog searchForDialog(CustomDialogs dialogType)
	{
		for (CustomDialog dialog : dialogs.values())
			if (dialog.getIdentifier().equals(dialogType))
				return dialog;
		throw new CannotFindSpecifiedDialogTypeException();
	}

	public void showOrHide(CustomDialogs dialogType)
	{
		CustomDialog dialog = searchForDialog(dialogType);
		showOrHide(dialog);
	}

	public void clear()
	{
		dialogs.clear();
	}
}
