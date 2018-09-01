package pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class WhiteMaskPixelModifier implements PixelModifier
{
	@Override
	public void modify(Pixmap destinationPixmap, Color oldColor)
	{
		destinationPixmap.setColor(1, 1, 1, 1);
	}
}
