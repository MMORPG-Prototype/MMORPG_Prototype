package pl.mmorpg.prototype.client.objects.spells;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import pl.mmorpg.prototype.client.objects.MovableGameObject;

public abstract class ThrowableObject extends MovableGameObject
{
    private Vector2 flyingVector = new Vector2(1000, 1000);

    public ThrowableObject(Texture lookout, long id)
    {
        super(lookout, id);
    }

    @Override
    public void update(float deltaTime)
    {
        setRotation(flyingVector.angle() - 90);
        super.update(deltaTime);
    }

    @Override
    public void setX(float x)
    {
        flyingVector.x = getX() - x;
        super.setX(x);
    }

    @Override
    public void setY(float y)
    {
        flyingVector.y = getY() - y;
        super.setY(y);
    }

    @Override
    public void setPosition(float x, float y)
    {
        setX(x);
        setY(y);
    }
    
    @Override
    public void setRegion(TextureRegion region)
    {
        setRegionWidth(region.getRegionWidth());
        super.setRegion(region);
    }
}
