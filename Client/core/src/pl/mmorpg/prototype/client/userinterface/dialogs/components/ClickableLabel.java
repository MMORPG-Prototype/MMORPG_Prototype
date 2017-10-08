package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class ClickableLabel extends Label
{
	private final Color defaultColor = new Color(1,1,1,1);
	private final Color mouseHoverColor = Color.CHARTREUSE;
	
	public ClickableLabel(CharSequence text, Runnable onClickAction)
	{
		super(text, Settings.DEFAULT_SKIN);
		addListenerHighlightingLabelOnMouseHover();
		addClickListener(onClickAction);
	}

	private void addListenerHighlightingLabelOnMouseHover()
	{
		this.addListener(new InputListener() 
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				ClickableLabel.this.setColor(mouseHoverColor);
			}
			
			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				ClickableLabel.this.setColor(defaultColor);
			}
			
		});
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
