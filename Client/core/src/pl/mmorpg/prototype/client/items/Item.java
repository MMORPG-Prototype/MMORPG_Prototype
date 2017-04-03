package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public abstract class Item extends Actor
{
	protected static final float WIDTH_WHEN_DRAGGED = 32;
	protected static final float HEIGHT_WHEN_DRAGGED = 32;
	protected static final float WIDTH_INVENTORY = 24;
	protected static final float HEIGHT_INVENTORY = 24;


	private final Sprite sprite;
	private final SpriteDrawable drawable;

	public Item(Texture texture)
	{
		sprite = new Sprite(texture);
		drawable = new SpriteDrawable(sprite);
		drawable.setMinWidth(WIDTH_INVENTORY);
		drawable.setMinHeight(HEIGHT_INVENTORY);
	}

	public Texture getTexture()
	{
		return sprite.getTexture();
	}


	public void renderWhenDragged(SpriteBatch batch)
	{
		batch.draw(sprite.getTexture(), getMouseX() - WIDTH_WHEN_DRAGGED / 2, getMouseY() - HEIGHT_WHEN_DRAGGED / 2,
				WIDTH_WHEN_DRAGGED, HEIGHT_WHEN_DRAGGED);
	}

	protected float getMouseX()
	{
		return Gdx.input.getX();
	}

	protected float getMouseY()
	{
		return Gdx.graphics.getHeight() - Gdx.input.getY();
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		batch.draw(sprite.getTexture(), getX() - WIDTH_INVENTORY / 2, getY() - HEIGHT_INVENTORY / 2, WIDTH_INVENTORY,
				HEIGHT_INVENTORY);
	}

	public SpriteDrawable getDrawable()
	{
		return drawable;
	}

	public abstract String getIdentifier();
}
