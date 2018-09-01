package pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class HighlightingPixelModifier implements PixelModifier
{
	@Override
	public void modify(Pixmap destinationPixmap, Color oldColor)
	{
		destinationPixmap.setColor(highlightColor(oldColor.r), highlightColor(oldColor.g), highlightColor(oldColor.b),
				highlightColor(oldColor.a));
	}

	private float highlightColor(float colorValue)
	{
		return colorValue + (1.0f - colorValue) / 2;
	}
}
