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
    private long id;
    protected static final float WIDTH_WHEN_DRAGGED = 36;
    protected static final float HEIGHT_WHEN_DRAGGED = 36;
    protected static final float WIDTH_INVENTORY = 32;
    protected static final float HEIGHT_INVENTORY = 32;

    private final Sprite sprite;
    private final SpriteDrawable drawable;

    public Item(Texture texture, long id)
    {
        this.id = id;
        sprite = new Sprite(texture);
        drawable = new SpriteDrawable(sprite);
        setWidth(WIDTH_INVENTORY);
        setHeight(HEIGHT_INVENTORY);
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
        draw(batch, getX(), getY());
    }

    public SpriteDrawable getDrawable()
    {
        return drawable;
    }

    public void setTexture(Texture texture)
    {
        sprite.setTexture(texture);
    }

    public abstract String getIdentifier();

    public void draw(Batch batch, float x, float y)
    {
        batch.draw(sprite.getTexture(), x, y, WIDTH_INVENTORY, HEIGHT_INVENTORY);
    }
    
    public long getId()
    {
        return id;
    }

	public abstract boolean shouldBeRemoved();

}
