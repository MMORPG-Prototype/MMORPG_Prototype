package pl.mmorpg.prototype.server.objects;

import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.clientservercommon.IdSupplier;
import pl.mmorpg.prototype.server.resources.Assets;

public class MapCollisionUnknownObject extends GameObject
{

    public MapCollisionUnknownObject(Rectangle collisionRectangle)
    {
        super(Assets.get("nullTexture.png"), IdSupplier.getId());
        super.setPosition(collisionRectangle.x, collisionRectangle.y);
        super.setSize(collisionRectangle.width, collisionRectangle.height);
    }

    @Override
    public void update(float deltaTime)
    {
    }

    @Override
    public void setX(float x)
    {
        throw new UnsupportedOperationException(MapCollisionUnknownObject.class.getName());
    }

    @Override
    public void setY(float y)
    {
        throw new UnsupportedOperationException(MapCollisionUnknownObject.class.getName());
    }

    @Override
    public void setPosition(float x, float y)
    {
        throw new UnsupportedOperationException(MapCollisionUnknownObject.class.getName());
    }

    @Override
    public void setSize(float width, float height)
    {
        throw new UnsupportedOperationException(MapCollisionUnknownObject.class.getName());
    }

}
