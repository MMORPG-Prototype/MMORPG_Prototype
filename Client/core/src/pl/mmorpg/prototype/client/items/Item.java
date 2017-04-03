package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item
{
	private Texture texture;

	public Item(Texture texture)
	{
		this.texture = texture;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void render(SpriteBatch batch)
	{
		batch.draw(texture, Gdx.input.getX() - 16, Gdx.graphics.getHeight() - Gdx.input.getY() - 16, 32, 32);
	}
}
