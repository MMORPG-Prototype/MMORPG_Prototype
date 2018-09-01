package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class GraphicGameObject implements Disposable
{
	protected float x;
	protected float y;

	public abstract void update(float deltaTime);

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public abstract void render(SpriteBatch batch);

	public abstract boolean shouldDelete();

	public void dispose()
	{
	}
}
