package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public abstract class GameObject extends Sprite implements RectangleCollisionObject, Identifiable
{
    private long id;
    private int layer = 0;

    public GameObject(Texture lookout, long id)
    {
        super(lookout);
        super.setRegion(lookout);
        this.setId(id);
    }
    
    @Override
    public Rectangle getCollisionRect()
    {
    	return getBoundingRectangle();
    }

    public void render(SpriteBatch batch)
    {
        draw(batch);
    }
    
    public TextureRegion getTextureRegion()
    {
    	return new TextureRegion(getTexture());
    }

    public abstract void update(float deltaTime);

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getIdentifier()
    {
        return ObjectsIdentifier.getObjectIdentifier(getClass());
    }

    public void onRemoval(GraphicObjectsContainer graphics)
    {
    }

	public int getLayer()
	{
		return layer;
	}

	public void setLayer(int layer)
	{
		this.layer = layer;
	}
	
	public static class CollisionMapGameObject extends GameObject
	{
		private Rectangle rectangle;

		public CollisionMapGameObject(Rectangle rectangle, int id)
		{
			super(Assets.get("nullTexture.png"), id);
			this.rectangle = rectangle;
		}
		
		@Override
	    public Rectangle getCollisionRect()
	    {
	    	return rectangle;
	    }

		@Override
		public void update(float deltaTime)
		{
		}
	}
}
