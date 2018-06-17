package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public class AutoCleanupOnCloseButtonDialog extends Dialog implements Identifiable
{
	private final long id;
	private final AutoCleanupCloseButton<AutoCleanupOnCloseButtonDialog> closeButton;

	public AutoCleanupOnCloseButtonDialog(String title, ActorManipulator linkedManipulator, long id)
	{
		super(title, Settings.DEFAULT_SKIN);
		this.id = id;
		
		closeButton = new AutoCleanupCloseButton<AutoCleanupOnCloseButtonDialog>(this, linkedManipulator) 
		{
			@Override
			public void onPress()
			{
				onClose();
			}
		};
		getTitleTable().add(closeButton).size(15, 15).padRight(-5).top().right();
		
	}
	
	@Override
	public long getId()
	{
		return id;
	}
	
	public void cleanUpItself()
	{
		closeButton.cleanUpDialog();
		onClose();
	}
	
	public void onClose()
	{
	}

}
