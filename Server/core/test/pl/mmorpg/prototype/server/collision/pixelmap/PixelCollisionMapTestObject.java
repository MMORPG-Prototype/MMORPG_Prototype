package pl.mmorpg.prototype.server.collision.pixelmap;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.server.collision.interfaces.RectangleCollisionObject;

public class PixelCollisionMapTestObject extends Rectangle implements RectangleCollisionObject
{
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
    
}
