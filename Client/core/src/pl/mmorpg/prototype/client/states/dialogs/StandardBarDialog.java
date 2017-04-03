package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class StandardBarDialog extends Table
{
	public StandardBarDialog()
	{
		super(Settings.DEFAULT_SKIN);

		Button menuButton = new Button(this.getSkin());
		add(menuButton);

		pack();
	}
}
