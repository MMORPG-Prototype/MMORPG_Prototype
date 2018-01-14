package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class GameWorldLabel extends TimedGraphic
{
	private static final float DEFAULT_LIVING_TIME = 3.0f;
	protected BitmapFont font = Assets.getFont();
	private final String message;

	public GameWorldLabel(String message, GameObject source)
	{
		super(DEFAULT_LIVING_TIME);
		this.message = message;
		x = source.getX();
		y = source.getY() + 40;
	}


	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, message, x, y);
	}
	
}
