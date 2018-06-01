package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MouseHoverHighlightingClickableLabel extends ClickableLabel
{
	private final Color defaultColor = new Color(1, 1, 1, 1);
	private final Color mouseHoverColor = Color.CHARTREUSE;

	public MouseHoverHighlightingClickableLabel(CharSequence text, Runnable onClickAction)
	{
		super(text, onClickAction);
		addListenerHighlightingLabelOnMouseHover();
	}

	private void addListenerHighlightingLabelOnMouseHover()
	{
		this.addListener(new InputListener()
		{
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor)
			{
				MouseHoverHighlightingClickableLabel.this.setColor(mouseHoverColor);
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor)
			{
				MouseHoverHighlightingClickableLabel.this.setColor(defaultColor);
			}
		});
	}

}
