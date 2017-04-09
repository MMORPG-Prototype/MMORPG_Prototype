package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ButtonCreator
{
	public static TextButton createTextButton(String label, Runnable onClickAction)
	{
		TextButton inventoryButton = new TextButton(label, Settings.DEFAULT_SKIN);

		inventoryButton.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				onClickAction.run();
			}
		});
		return inventoryButton;
	}
}
