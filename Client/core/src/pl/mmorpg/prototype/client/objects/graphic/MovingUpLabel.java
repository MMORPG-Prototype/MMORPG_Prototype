package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.client.objects.GameObject;

public abstract class MovingUpLabel extends GameWorldLabel
{
	private Color color;
	private float moveUpSpeed = 35.0f;

	public MovingUpLabel(String message, GameObject source, Color color)
	{
		super(message, source);
		this.color = color;
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		font.setColor(color);
		super.render(batch);
	}
	
	@Override
	public void update(float delta)
	{
		y += moveUpSpeed * delta;
		super.update(delta);
	}

}
