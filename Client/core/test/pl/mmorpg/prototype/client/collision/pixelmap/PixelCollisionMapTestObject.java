package pl.mmorpg.prototype.client.collision.pixelmap;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.client.collision.interfaces.RectangleCollisionObject;
import pl.mmorpg.prototype.clientservercommon.Identifiable;

public class PixelCollisionMapTestObject extends Rectangle implements RectangleCollisionObject, Identifiable
{
	private static int nextId = 0;
	private final int id = nextId++;
	
    public PixelCollisionMapTestObject()
    {
    	super();
    }

    public PixelCollisionMapTestObject(float x, float y, float width, float height)
    {
        super(x, y, width, height);
    }

    public PixelCollisionMapTestObject(Rectangle rect)
    {
        super(rect);
    }

    @Override
    public Rectangle getCollisionRect()
    {
        return this;
    }

	@Override
	public long getId()
	{
		return id;
	}
    
}
