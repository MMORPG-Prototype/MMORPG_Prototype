package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ShortcutBarDialog extends Dialog
{
	public ShortcutBarDialog()
	{
		super("", Settings.DEFAULT_SKIN, "secondary");

		button("Menu").right().bottom();

		this.setX(900);
		this.setWidth(500);
		this.setHeight(50);
		setMovable(false);

	}
}
