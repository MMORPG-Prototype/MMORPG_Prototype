package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;

public class ShortcutBarDialog extends Dialog
{
	public ShortcutBarDialog(UserInterface linkedInterface)
	{
		super("", Settings.DEFAULT_SKIN);

		TextButton menuButton = new TextButton("Menu", getSkin());
		add(menuButton).right().bottom();

		menuButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				linkedInterface.showOrHideDialog(MenuDialog.class);
			}
		});
	}
}
