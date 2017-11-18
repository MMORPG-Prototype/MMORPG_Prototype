package pl.mmorpg.prototype.client.objects.icons.items;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.objects.icons.DraggableIcon;

public abstract class ItemIcon extends DraggableIcon
{
    private long id;

    public ItemIcon(Texture texture, long id)
    {
    	super(texture);
        this.id = id;
    }
    
    public long getId()
    {
        return id;
    }

    public abstract String getIdentifier();
    
	public abstract boolean shouldBeRemoved();
}
