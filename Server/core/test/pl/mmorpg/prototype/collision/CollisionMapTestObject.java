package pl.mmorpg.prototype.collision;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.server.collision.CollisionObject;

public class CollisionMapTestObject extends Rectangle implements CollisionObject
{
    public CollisionMapTestObject()
    {
        super();
    }

    public CollisionMapTestObject(float x, float y, float width, float height)
    {
        super(x, y, width, height);
    }

    public CollisionMapTestObject(Rectangle rect)
    {
        super(rect);
    }

    @Override
    public Rectangle getCollisionRect()
    {
        return this;
    }
    
}
