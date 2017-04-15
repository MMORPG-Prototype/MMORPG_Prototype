package pl.mmorpg.prototype.server.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import pl.mmorpg.prototype.server.collision.CollisionObject;

public abstract class GameObject implements CollisionObject
{
    public static final NullGameObject NULL_OBJECT = new NullGameObject();

    private Rectangle collisionRectangle = new Rectangle();
    private Sprite sprite;
    private long id = -1;
    private int layer;

    public GameObject(Texture lookout, long id)
    {
        sprite = new Sprite(lookout);
        sprite.setRegion(lookout);
        collisionRectangle.width = lookout.getWidth();
        collisionRectangle.height = lookout.getHeight();
        this.id = id;
    }

    private GameObject()
    {
    }

    public abstract void update(float deltaTime);

    public void render(SpriteBatch batch)
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

    public int getLayer()
    {
        return layer;
    }

    public void setLayer(int layer)
    {
        this.layer = layer;
    }

    @Override
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

    public float getWidth()
    {
        return collisionRectangle.getWidth();
    }

    public float getHeight()
    {
        return collisionRectangle.getHeight();
    }

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

    private static class NullGameObject extends GameObject
    {

        @Override
        public void update(float deltaTime)
        {
        }

        @Override
        public void render(SpriteBatch batch)
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
        public int getLayer()
        {
            return -1;
        }

        @Override
        public void setLayer(int layer)
        {
        }

        @Override
        public Rectangle getCollisionRect()
        {
            return new Rectangle(-1, -1, -1, -1);
        }

        @Override
        public float getX()
        {
            return -1;
        }

        @Override
        public float getY()
        {
            return -1;
        }

        @Override
        public long getId()
        {
            return -1;
        }

        @Override
        public void setId(long id)
        {
        }

        @Override
        public String getIdentifier()
        {
            return "NullObject";
        }
    }

}
