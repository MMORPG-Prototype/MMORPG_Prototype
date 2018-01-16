package pl.mmorpg.prototype.client.objects.graphic;

import java.util.function.Supplier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import pl.mmorpg.prototype.client.objects.GameObject;

public class GameObjectHighlightGraphic extends GraphicGameObject
{
	private static final float MAX_LIVING_TIME = 0.5f;
	private GameObject gameObject;
	private final Texture highlightingGraphic;
	private Supplier<Boolean> removalCondition;

	public GameObjectHighlightGraphic(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		this.gameObject = gameObject;
		this.removalCondition = removalCondition;
		this.highlightingGraphic = createHighlightingGraphic(gameObject.getTextureRegion());
	}

	private Texture createHighlightingGraphic(TextureRegion textureRegion)
	{
		TextureData textureData = textureRegion.getTexture().getTextureData();
		textureData.prepare();
		Pixmap sourcePixmap = textureData.consumePixmap();
		Pixmap destinationPixmap = new Pixmap(textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), Format.RGBA8888);
		Color color = new Color();

		for (int x = 0; x < textureRegion.getRegionWidth(); x++)
		{
			for (int y = 0; y < textureRegion.getRegionHeight(); y++)
			{
				int colorInt = sourcePixmap.getPixel(textureRegion.getRegionX() + x, textureRegion.getRegionY() + y);
				Color.rgba8888ToColor(color, colorInt);
				destinationPixmap.setColor(1.0f, 1f, 1.0f, 1);
				if (color.a > 0.004f)
					destinationPixmap.drawPixel(x, y);
			}
		}
		Texture result = new Texture(destinationPixmap);
		textureData.disposePixmap();
		destinationPixmap.dispose();
		return result;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
		batch.draw(highlightingGraphic, gameObject.getX(), gameObject.getY(), gameObject.getWidth(),
				gameObject.getHeight());
		batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public boolean shouldDelete()
	{
		return removalCondition.get();
	}

	@Override
	public void update(float deltaTime)
	{
	}
}
