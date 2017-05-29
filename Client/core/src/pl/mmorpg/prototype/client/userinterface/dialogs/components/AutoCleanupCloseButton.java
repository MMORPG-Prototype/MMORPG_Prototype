package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.input.ActorManipulator;

public class AutoCleanupCloseButton extends CloseButton
{

	public <T extends Table & Identifiable> AutoCleanupCloseButton(T dialogToClose, ActorManipulator linkedDialogs)
	{
		super(dialogToClose);
		
		this.addListener(new ClickListener()
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{ 
				linkedDialogs.removeDialog(dialogToClose);
			}
		});
	}
	

}
