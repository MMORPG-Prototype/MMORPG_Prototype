package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;

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
}
