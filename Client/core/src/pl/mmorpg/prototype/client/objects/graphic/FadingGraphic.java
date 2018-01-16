package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class FadingGraphic extends GraphicGameObject
{
	private static final float totalFadingTime = 3.0f;
	private float currentFadingTime = 0.0f;
	private Texture texture;
	private Rectangle bounds; 

	public FadingGraphic(Texture texture, Rectangle bounds)
	{
		this.texture = texture;
		this.bounds = bounds;
		setX(bounds.getX());
		setY(bounds.getY());
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		float currentFade = (totalFadingTime - currentFadingTime)/totalFadingTime;
		batch.setColor(1.0f, 1.0f, 1.0f, currentFade);
		batch.draw(texture, x, y, bounds.width, bounds.height);
		batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void update(float deltaTime)
	{
		currentFadingTime += deltaTime;
	}

	@Override
	public boolean shouldDelete()
	{
		return currentFadingTime >= totalFadingTime; 
	}
}
