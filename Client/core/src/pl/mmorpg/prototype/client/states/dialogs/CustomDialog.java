package pl.mmorpg.prototype.client.states.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class CustomDialog extends Dialog
{
	public CustomDialog(String title, Skin skin)
	{
		super(title, skin);
	}

	public abstract CustomDialogs getIdentifier();
}
