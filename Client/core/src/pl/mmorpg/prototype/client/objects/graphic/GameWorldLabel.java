package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.userinterface.GraphicGameObject;

public class GameWorldLabel extends GraphicGameObject
{
	private static final float maxLivingTime = 3.0f;
	private BitmapFont font = Assets.getFont();
	private float currentLivingTime = 0.0f;
	private final String message;
	private GameObject source;

	public GameWorldLabel(String message, GameObject source)
	{
		this.message = message;
		this.source = source;
	}

	public void update(float delta)
	{
		x = source.getX();
		y = source.getY() + 40;
		currentLivingTime += delta;
		if (currentLivingTime > maxLivingTime)
			isAlive = false;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		font.draw(batch, message, x, y);
	}
}
