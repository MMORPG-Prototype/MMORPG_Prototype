package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class CloseButton extends Button
{
	public CloseButton(Dialog dialogToClose)
	{
		super(Settings.DEFAULT_SKIN, "close");

		this.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				dialogToClose.setVisible(false);
			}
		});
	}

}
