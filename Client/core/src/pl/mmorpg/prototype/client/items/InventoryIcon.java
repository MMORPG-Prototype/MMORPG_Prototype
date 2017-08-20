package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public abstract class InventoryIcon extends Actor
{
	private static final float WIDTH_INVENTORY = 32;
	private static final float HEIGHT_INVENTORY = 32;

	final Sprite sprite;
	private final SpriteDrawable drawable;

	public InventoryIcon(Texture texture)
	{
		sprite = new Sprite(texture);
		drawable = new SpriteDrawable(sprite);
		setWidth(WIDTH_INVENTORY);
		setHeight(HEIGHT_INVENTORY);
		drawable.setMinWidth(WIDTH_INVENTORY);
		drawable.setMinHeight(HEIGHT_INVENTORY);
	}

	public SpriteDrawable getDrawable()
	{
		return drawable;
	}
	
    public Texture getTexture()
    {
        return sprite.getTexture();
    }

    protected float getMouseX()
    {
        return Gdx.input.getX();
    }

    protected float getMouseY()
    {
        return Gdx.graphics.getHeight() - Gdx.input.getY();
    }
 
    public void setTexture(Texture texture)
    {
        sprite.setTexture(texture);
    }
	
	@Override
    public void draw(Batch batch, float parentAlpha)
    {
        draw(batch, getX(), getY());
    }
	
	public void draw(Batch batch, float x, float y)
    {
        batch.draw(sprite.getTexture(), x, y, WIDTH_INVENTORY, HEIGHT_INVENTORY);
    }
	
}
