package pl.mmorpg.prototype.client.objects.icons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Icon extends Actor
{
	private static final float WIDTH_INVENTORY = 32;
	private static final float HEIGHT_INVENTORY = 32;

	protected final Sprite sprite;
	private final SpriteDrawable drawable;

	public Icon(Texture texture)
	{
		this(texture, WIDTH_INVENTORY, HEIGHT_INVENTORY);
	}
	
	public Icon(Texture texture, float width, float height)
	{
		sprite = new Sprite(texture);
		drawable = new SpriteDrawable(sprite);
		setWidth(width);
		setHeight(height);
	}
	
	@Override
	public void setWidth(float width)
	{
		super.setWidth(width);
		drawable.setMinWidth(width);
	}
	
	@Override
	public void setHeight(float height)
	{
		super.setHeight(height);
		drawable.setMinHeight(height);
	}
	
	protected Sprite getSprite()
	{
		return sprite;
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
        batch.draw(sprite.getTexture(), x, y, getWidth(), getHeight());
    }
}
