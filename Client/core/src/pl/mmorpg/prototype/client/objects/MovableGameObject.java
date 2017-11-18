package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

public abstract class MovableGameObject extends GameObject
{
    private final static float stopMovingTimeBound = 0.2f;
    private float stopMovingTime = 0.0f;
    private float stepSpeed = 150.0f;
    private float targetX = 100.0f;
    private float targetY = 100.0f;
    private boolean slidingActive = true;
    private float lastDeltaTime;
    private int lastMoveDirection = Directions.NONE;

    public MovableGameObject(Texture lookout, long id)
    {
        super(lookout, id);
        targetX = getX();
        targetY = getY();
    }

    public void initPosition(float x, float y)
    {
        targetX = x;
        targetY = y;
        super.setX(x);
        super.setY(y);
    }

    @Override
    public void update(float deltaTime)
    {
        lastDeltaTime = deltaTime;
        repositionX(deltaTime);
        repositionY(deltaTime);
        handleMovementStop(deltaTime);
    }

    private void handleMovementStop(float deltaTime)
    {
        stopMovingTime += deltaTime;
        if (stopMovingTime > stopMovingTimeBound)
        {
            stopMovingTime = 0.0f;
            lastMoveDirection = Directions.NONE;
        }
    }

    private void repositionX(float deltaTime)
    {
        float deltaX = targetX - getX();
        float stepValue = stepSpeed * deltaTime;
        if(slidingActive)
        	stepValue *= (Math.abs(deltaX) / 30);
        if (deltaX < 0)
            stepValue = -stepValue;
        if (Math.abs(deltaX) > Math.abs(stepValue))
            super.setX(getX() + stepValue);
        else
            super.setX(targetX);
    }

    private void repositionY(float deltaTime)
    {
        float deltaY = targetY - getY();
        float stepValue = stepSpeed * deltaTime;
        if(slidingActive)
        	stepValue *= (Math.abs(deltaY) / 30);
        if (deltaY < 0)
            stepValue = -stepValue;
        if (Math.abs(deltaY) > Math.abs(stepValue))
            super.setY(getY() + stepValue);
        else
            super.setY(targetY);
    }

    @Override
    public void setX(float x)
    {
        if (x > getX())
            lastMoveDirection = Directions.RIGHT;
        else if (x < getX())
            lastMoveDirection = Directions.LEFT;
        targetX = x;
    }

    @Override
    public void setY(float y)
    {
        if (y > getY())
            lastMoveDirection = Directions.UP;
        else if (y < getY())
            lastMoveDirection = Directions.DOWN;
        targetY = y;
    }

    @Override
    public void setPosition(float x, float y)
    {
        setX(x);
        setY(y);
    }

    public int getMoveDirection()
    {   
    	if(!slidingActive)
    		return lastMoveDirection;
    	
        float deltaX = targetX - getX();
        float deltaY = targetY - getY();
        
        if(deltaX == 0 && deltaY == 0)
            return Directions.NONE;
        
        if(Math.abs(deltaX) > Math.abs(deltaY))
            return deltaX > 0 ? Directions.RIGHT : Directions.LEFT;
        return deltaY > 0 ? Directions.UP : Directions.DOWN;
    }

    public boolean isNearTarget()
    {
        float stepValue = stepSpeed * lastDeltaTime;
        float deltaY = targetY - getY();
        float deltaX = targetX - getX();
        return Math.abs(deltaY) <= stepValue && Math.abs(deltaX) <= stepValue;
    }
    
    public void disableSliding()
    {
    	slidingActive = false;
    }
    
    public void setStepSpeed(float stepSpeed)
    {
        this.stepSpeed = stepSpeed;
    }
}
