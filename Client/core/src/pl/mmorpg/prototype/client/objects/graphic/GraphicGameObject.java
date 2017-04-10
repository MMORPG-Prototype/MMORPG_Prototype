package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicGameObject 
{
	private boolean isAlive = true;
	protected float x;
	protected float y;

	public boolean isAlive()
	{
		return isAlive;
	}
	
	public void update(float deltaTime)
	{
		if(shouldDelete())
			isAlive = false;
	}
	
	public abstract void render(SpriteBatch batch);
	
	public abstract boolean shouldDelete();
}
