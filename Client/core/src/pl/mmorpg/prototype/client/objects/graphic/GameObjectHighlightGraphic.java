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
import pl.mmorpg.prototype.client.objects.graphic.helpers.HighlightedTextureCreator;

public class GameObjectHighlightGraphic extends GraphicGameObject
{
	private GameObject gameObject;
	private final Texture highlightingGraphic;
	private Supplier<Boolean> removalCondition;

	public GameObjectHighlightGraphic(GameObject gameObject, Supplier<Boolean> removalCondition)
	{
		this.gameObject = gameObject;
		this.removalCondition = removalCondition;
		this.highlightingGraphic = HighlightedTextureCreator.createHighlightingMask(gameObject.getTextureRegion());
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

	@Override
	public void dispose()
	{
		highlightingGraphic.dispose();
	}
}
