package pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public interface PixelModifier
{
	void modify(Pixmap destinationPixmap, Color oldColor);
}
