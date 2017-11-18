package pl.mmorpg.prototype.client.objects.icons.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class DraggableItemIcon extends ItemIcon
{
	protected static final float WIDTH_WHEN_DRAGGED = 36;
	protected static final float HEIGHT_WHEN_DRAGGED = 36;
	
	public DraggableItemIcon(Texture texture, long id)
	{
		super(texture, id);
	}
	
	public void renderWhenDragged(SpriteBatch batch)
	{
	    batch.draw(sprite.getTexture(), getMouseX() - WIDTH_WHEN_DRAGGED / 2, getMouseY() - HEIGHT_WHEN_DRAGGED / 2,
	            WIDTH_WHEN_DRAGGED, HEIGHT_WHEN_DRAGGED);
	}

}