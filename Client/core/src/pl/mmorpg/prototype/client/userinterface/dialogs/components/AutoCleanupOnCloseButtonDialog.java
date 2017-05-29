package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.states.helpers.Settings;

public class AutoCleanupOnCloseButtonDialog extends Dialog implements Identifiable
{
	private int id;

	public AutoCleanupOnCloseButtonDialog(String title, ActorManipulator linkedContainer, int id)
	{
		super(title, Settings.DEFAULT_SKIN);
		this.id = id;
		
		CloseButton closeButton = new AutoCleanupCloseButton(this, linkedContainer);
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();
		
	}
	
	@Override
	public int getId()
	{
		return id;
	}

}
