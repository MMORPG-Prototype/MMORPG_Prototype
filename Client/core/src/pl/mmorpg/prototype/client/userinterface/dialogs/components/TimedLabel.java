package pl.mmorpg.prototype.client.userinterface.dialogs.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import pl.mmorpg.prototype.client.states.helpers.Settings;

public class TimedLabel extends Label
{
	private final float maxLivingTime;
	private float currentLivingTime = 0.0f;

	public TimedLabel(CharSequence text, float livingTime)
	{
		super(text, Settings.DEFAULT_SKIN);
		this.maxLivingTime = livingTime;
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		currentLivingTime += Gdx.graphics.getDeltaTime();
		if(currentLivingTime >= maxLivingTime)
			this.remove();
	}
}
