package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

import pl.mmorpg.prototype.client.states.helpers.Settings;
import pl.mmorpg.prototype.client.userinterface.dialogs.DialogUtils;

public class MouseHoverVisibleDialog extends Dialog
{
	public MouseHoverVisibleDialog(Actor sourceActor, String name)
	{
		super(name, Settings.DEFAULT_SKIN);
		setVisible(false);
		sourceActor.addListener(new InputListener() 
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				Actor topDialogActor = DialogUtils.getTopActorOf(sourceActor);
				setPosition(topDialogActor.getRight(), topDialogActor.getY());
				setVisible(true);
				toFront();
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				setVisible(false);
				toBack();
			}

			@Override
			public boolean mouseMoved(InputEvent event, float x, float y)
			{
				return true;
			}
			
		});
	}
}
