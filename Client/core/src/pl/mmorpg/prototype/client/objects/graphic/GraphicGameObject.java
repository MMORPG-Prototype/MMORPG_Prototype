package pl.mmorpg.prototype.client.objects.graphic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicGameObject
{
    protected float x;
    protected float y;

    public abstract void update(float deltaTime);
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }

    public abstract void render(SpriteBatch batch);

    public abstract boolean shouldDelete();
}
