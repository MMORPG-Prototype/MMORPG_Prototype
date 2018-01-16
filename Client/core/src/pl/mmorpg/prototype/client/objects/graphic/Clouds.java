package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.resources.Assets;

public class Clouds extends GraphicGameObject
{
	private static final float moveXPerSecond = 4.0f;
	private static final float moveYPerSecond = -4.0f;
	private final static Texture cloudsTexture = Assets.get("cloud.png");
	
	static Texture getTexture()
	{
		return cloudsTexture;
	}
	
	public Clouds(float startX, float startY)
	{
		this.x = startX;
		this.y = startY;
	}
	
	@Override
	public void update(float deltaTime)
	{ 
		x += moveXPerSecond*deltaTime;
		y += moveYPerSecond*deltaTime;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(cloudsTexture, x, y);
	}

	@Override
	public boolean shouldDelete()
	{
		return false;
	}
}
