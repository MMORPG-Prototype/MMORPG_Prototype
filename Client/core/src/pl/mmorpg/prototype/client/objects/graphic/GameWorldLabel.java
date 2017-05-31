package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.GameObject;
import pl.mmorpg.prototype.client.resources.Assets;

public class GameWorldLabel extends GraphicGameObject
{
	private final float maxLivingTime = 3.0f;
	protected BitmapFont font = Assets.getFont();
	private float currentLivingTime = 0.0f;
	private final String message;

	public GameWorldLabel(String message, GameObject source)
	{
		this.message = message;
		x = source.getX();
		y = source.getY() + 40;
	}


	@Override
	public void update(float delta)
	{
		currentLivingTime += delta;
		super.update(delta);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, message, x, y);
	}
	
	@Override
	public boolean shouldDelete()
	{
		return currentLivingTime > maxLivingTime;
	}
}
