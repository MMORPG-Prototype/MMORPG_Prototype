package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ClickableLabel extends Label
{
	
	public ClickableLabel(CharSequence text, Runnable onClickAction)
	{
		super(text, Settings.DEFAULT_SKIN);
		addClickListener(onClickAction);
	}
	
	private void addClickListener(Runnable onClick)
	{	
		this.addListener(new ClickListener() 
		{
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				onClick.run();
			}
		});
	}

}
