package pl.mmorpg.prototype.client.objects.icons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class DraggableIcon extends Icon
{
	protected static final float WIDTH_WHEN_DRAGGED = 36;
	protected static final float HEIGHT_WHEN_DRAGGED = 36;
	private static final Batch newBatch = new SpriteBatch();

	public DraggableIcon(Texture texture)
	{
		super(texture);
	}
	
	public void renderWhenDragged(Batch batch)
	{
		batch.draw(sprite.getTexture(), getMouseX() - WIDTH_WHEN_DRAGGED / 2, getMouseY() - HEIGHT_WHEN_DRAGGED / 2,
	            WIDTH_WHEN_DRAGGED, HEIGHT_WHEN_DRAGGED);
	}

}
