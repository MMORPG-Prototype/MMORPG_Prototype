package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

public abstract class MovableGameObject extends GameObject
{
	private static float stopMovingTimeBound = 0.2f;
	private float stopMovingTime = 0.0f;
    private float stepSpeed = 100.0f;
    private float targetX = 100.0f;
    private float targetY = 100.0f;
    private float lastDeltaTime;
    private int lastMoveDirection = Directions.NONE;

    public MovableGameObject(Texture lookout, long id)
    {
        super(lookout, id);
        targetX = getX();
        targetY = getY();
    }

    @Override
    public void update(float deltaTime)
    {
    	if(lastDeltaTime == 0.0f)
    	{
    		super.setX(targetX);
    		super.setY(targetY);
    	}
        lastDeltaTime = deltaTime;
        repositionX(deltaTime);
        repositionY(deltaTime);
        handleMovementStop(deltaTime);
    }

	private void handleMovementStop(float deltaTime)
	{
		stopMovingTime += deltaTime;
        if(stopMovingTime > stopMovingTimeBound)
        {
        	stopMovingTime = 0.0f;
        	lastMoveDirection = Directions.NONE;
        }
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
    	if(x > getX())
    		lastMoveDirection = Directions.RIGHT;
    	else if(x < getX())
    		lastMoveDirection = Directions.LEFT;
        targetX = x;
    }

    @Override
    public void setY(float y)
    {
    	if(y > getY())
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
   
    
    public int getLastMoveDirection()
    {
    	return lastMoveDirection;
    }   
    
    public boolean isNearTarget()
    {
        float stepValue = stepSpeed*lastDeltaTime;
        float deltaY = targetY - getY();
        float deltaX = targetX - getX();
        return Math.abs(deltaY) <= stepValue && Math.abs(deltaX) <= stepValue;
    }
}
