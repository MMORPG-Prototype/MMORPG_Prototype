package pl.mmorpg.prototype.server.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject
{
	public static final NullGameObject NULL_OBJECT = new NullGameObject();
	private Sprite sprite;

	private Rectangle collisionRectangle;

	private int layer;

	public GameObject(Texture lookout)
	{
		sprite.setTexture(lookout);
		this.setSize(lookout.getWidth(), lookout.getHeight());
	}

	private GameObject()
	{
	}

	protected abstract void update(float deltaTime);

	protected void render(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	public void setX(float x)
	{
		sprite.setX(x);
		collisionRectangle.setX(x);
	}

	public void setY(float y)
	{
		sprite.setY(y);
		collisionRectangle.setY(y);
	}

	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
		collisionRectangle.setPosition(x, y);
	}

	public void setSize(float width, float height)
	{
		sprite.setSize(width, height);
		collisionRectangle.setSize(width, height);
	}

	public void setBounds(float x, float y, float width, float height)
	{
		sprite.setBounds(x, y, width, height);
		collisionRectangle.set(x, y, width, height);
	}

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}

	public Rectangle getCollisionRect()
	{
		return collisionRectangle;
	}

	public float getX()
	{
		return collisionRectangle.x;
	}

	public float getY()
	{
		return collisionRectangle.y;
	}

	private static class NullGameObject extends GameObject
	{

		@Override
		protected void update(float deltaTime)
		{
		}

		@Override
		protected void render(SpriteBatch batch)
		{
		}

		@Override
		public void setX(float x)
		{
		}

		@Override
		public void setY(float y)
		{
		}

		@Override
		public void setPosition(float x, float y)
		{
		}

		@Override
		public void setSize(float width, float height)
		{
		}

		@Override
		public void setBounds(float x, float y, float width, float height)
		{
		}

		@Override
		public int getLayer()
		{
			return -1;
		}

		@Override
		public void setLayer(int layer)
		{
		}

	}
}
