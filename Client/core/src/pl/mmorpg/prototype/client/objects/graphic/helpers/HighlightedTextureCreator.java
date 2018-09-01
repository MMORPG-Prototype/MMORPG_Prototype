package pl.mmorpg.prototype.client.objects.graphic.helpers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier.HighlightingPixelModifier;
import pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier.WhiteMaskPixelModifier;
import pl.mmorpg.prototype.client.objects.graphic.helpers.piexel.modifier.PixelModifier;

public class HighlightedTextureCreator
{
	public static Texture createHighlightingMask(TextureRegion textureRegion)
	{
		return copyTextureWithModifications(textureRegion, new WhiteMaskPixelModifier());
	}

	public static Texture createHighlightedGraphic(TextureRegion textureRegion)
	{
		return copyTextureWithModifications(textureRegion, new HighlightingPixelModifier());
	}

	private static Texture copyTextureWithModifications(TextureRegion textureRegion, PixelModifier pixelModifier) {
		TextureData textureData = textureRegion.getTexture().getTextureData();
		if (!textureData.isPrepared())
			textureData.prepare();
		Pixmap sourcePixmap = textureData.consumePixmap();
		Pixmap destinationPixmap = new Pixmap(textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), Pixmap.Format.RGBA8888);
		Color color = new Color();

		for (int x = 0; x < textureRegion.getRegionWidth(); x++)
		{
			for (int y = 0; y < textureRegion.getRegionHeight(); y++)
			{
				int colorInt = sourcePixmap.getPixel(textureRegion.getRegionX() + x, textureRegion.getRegionY() + y);
				Color.rgba8888ToColor(color, colorInt);
				pixelModifier.modify(destinationPixmap, color);
				if (color.a > 0.004f)
					destinationPixmap.drawPixel(x, y);
			}
		}
		Texture result = new Texture(destinationPixmap);
		textureData.disposePixmap();
		destinationPixmap.dispose();
		return result;
	}
}
