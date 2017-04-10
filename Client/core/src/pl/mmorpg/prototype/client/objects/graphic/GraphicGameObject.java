package pl.mmorpg.prototype.client.userinterface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicGameObject 
{
	protected boolean isAlive = true;
	protected float x;
	protected float y;

	public boolean isAlive()
	{
		return isAlive;
	}
	
	public abstract void update(float deltaTime);
	
	public abstract void render(SpriteBatch batch);
}
