package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

public abstract class MovableGameObject extends GameObject
{
    private float stepSpeed = 100.0f;
    private float targetX = 100.0f;
    private float targetY = 100.0f;
    private float lastDeltaTime;

    public MovableGameObject(Texture lookout, long id)
    {
        super(lookout, id);
        targetX = getX();
        targetY = getY();
    }

    @Override
    public void update(float deltaTime)
    {
        lastDeltaTime = deltaTime;
        repositionX(deltaTime);
        repositionY(deltaTime);
    }

    private void repositionX(float deltaTime)
    {
        float deltaX = targetX - getX();
        float stepValue = stepSpeed*deltaTime;
        if(deltaX < 0)
            stepValue = -stepValue;
        if (Math.abs(deltaX) > Math.abs(stepValue))
            super.setX(getX() + stepValue);
        else
            super.setX(targetX);
    }

    private void repositionY(float deltaTime)
    {
        float deltaY = targetY - getY();
        float stepValue = stepSpeed*deltaTime;
        if(deltaY < 0)
            stepValue = -stepValue;
        if (Math.abs(deltaY) > Math.abs(stepValue))
            super.setY(getY() + stepValue);
        else
            super.setY(targetY);
    }

    @Override
    public void setX(float x)
    {
        targetX = x;
    }

    @Override
    public void setY(float y)
    {
        targetY = y;
    }

    @Override
    public void setPosition(float x, float y)
    {
        targetX = x;
        targetY = y;
    }
    
    public boolean isNearTarget()
    {
        float stepValue = stepSpeed*lastDeltaTime;
        float deltaY = targetY - getY();
        float deltaX = targetX - getX();
        return Math.abs(deltaY) <= stepValue && Math.abs(deltaX) <= stepValue;
    }
}
