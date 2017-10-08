package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.input.ActorManipulator;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public class AutoCleanupCloseButton<T extends Table & Identifiable> extends CloseButton
{
	private T dialogToClose;
	private ActorManipulator linkedDialogs;

	public AutoCleanupCloseButton(T dialogToClose, ActorManipulator linkedDialogs)
	{
		super(dialogToClose);
		this.dialogToClose = dialogToClose;
		this.linkedDialogs = linkedDialogs;
		
		this.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{ 
				cleanUpDialog();
				onPress();
			}

		});
	}
	
	public void onPress()
	{
	}
	
	public void cleanUpDialog()
	{
		linkedDialogs.removeDialog(dialogToClose);
		dialogToClose.remove();
	}

}
