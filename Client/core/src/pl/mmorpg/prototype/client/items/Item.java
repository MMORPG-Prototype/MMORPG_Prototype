package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public abstract class Item extends InventoryIcon
{
    private long id;

    public Item(Texture texture, long id)
    {
    	super(texture);
        this.id = id;
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

    public abstract String getIdentifier();
    
    public long getId()
    {
        return id;
    }

	public abstract boolean shouldBeRemoved();

}
