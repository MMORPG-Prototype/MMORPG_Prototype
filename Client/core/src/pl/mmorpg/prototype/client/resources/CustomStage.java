package pl.mmorpg.prototype.client.resources;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class CustomStage extends Stage
{
	private boolean isUsed = true;

	public CustomStage(ScalingViewport scalingViewport)
	{
		super(scalingViewport);
	}

	public boolean isUsed()
	{
		return isUsed;
	}

	public void setUsed(boolean used)
	{
		isUsed = used;
	}

	@Override
	public void dispose()
	{
		clear();
		isUsed = false;
	}
}
