package pl.mmorpg.prototype.client.userinterface.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.UserInterface;

public class ShortcutBarPane extends ScrollPane
{
	public ShortcutBarPane(UserInterface likedInterface)
	{
		super(new ShortcutBarDialog(likedInterface), Settings.DEFAULT_SKIN);

		this.setX(950);
		this.setWidth(450);
		this.setHeight(50);
	}
	
}
