package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.resources.Assets;

public abstract class StackableItem extends Item
{
	private Integer count = 0;
	private final BitmapFont font = Assets.getFont();

	public StackableItem(Texture texture)
	{
		super(texture);
	}

	public void addItem()
	{
		count++;
	}

	public void decreaseItemCount()
	{
		count--;
	}

	public int getItemCount()
	{
		return count;
	}

	public boolean isDepleted()
	{
		return count == 0;
	}

	@Override
	public void renderWhenDragged(SpriteBatch batch)
	{
		super.renderWhenDragged(batch);
		font.draw(batch, count.toString(), getMouseX() + 6, getMouseY() - 2);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		font.draw(batch, count.toString(), getX() + 4, getY() - 2);
	}
}
