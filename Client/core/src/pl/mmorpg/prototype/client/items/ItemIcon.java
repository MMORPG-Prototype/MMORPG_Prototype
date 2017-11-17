package pl.mmorpg.prototype.client.items;

import com.badlogic.gdx.graphics.Texture;

public abstract class ItemIcon extends Icon
{
    private long id;

    public ItemIcon(Texture texture, long id)
    {
    	super(texture);
        this.id = id;
    }

    public abstract String getIdentifier();
    
    public long getId()
    {
        return id;
    }

	public abstract boolean shouldBeRemoved();

}
