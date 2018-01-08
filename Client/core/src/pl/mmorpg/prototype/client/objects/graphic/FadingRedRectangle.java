package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.resources.Assets;

public class FadingRedRectangle extends FadingGraphic
{
	public FadingRedRectangle(Rectangle bounds)
	{
		super(Assets.get("redRectangle.png"), bounds);
	}
	
}
