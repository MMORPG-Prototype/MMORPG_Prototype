package pl.mmorpg.prototype.client.objects;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.client.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;

public abstract class MovableGameObject extends GameObject
{
	private final static float stopMovingTimeBound = 0.2f;
	private float stopMovingTime = 0.0f;
	private float stepSpeed = 150.0f;
	private float targetX = 100.0f;
	private float targetY = 100.0f;
	private boolean slidingActive = true;
	private final MoveInfo currentMoveInfo = new MoveInfo();
	private CollisionMap<GameObject> linkedCollisionMap;

	public MovableGameObject(Texture lookout, long id, CollisionMap<GameObject> linkedCollisionMap)
	{
		super(lookout, id);
		this.linkedCollisionMap = linkedCollisionMap;
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
			currentMoveInfo.setMoveDirection(Directions.NONE);
		}
	}

	private void repositionX(float deltaTime)
	{
		float deltaX = targetX - getX();
		float stepValue = stepSpeed * deltaTime;
		if (slidingActive)
			stepValue *= (Math.abs(deltaX) / 30);
		if (deltaX < 0)
			stepValue = -stepValue;
		if (Math.abs(deltaX) > Math.abs(stepValue))
			directPositionChangeX(getX() + stepValue);
		else
			super.setX(targetX);
	}

	private void directPositionChangeX(float stepValue)
	{
		super.setX(stepValue);
		if(stepValue < 0)
			linkedCollisionMap.repositionGoingLeft((int)stepValue, this);
		else
			linkedCollisionMap.repositionGoingRight((int)stepValue, this);
	}

	private void repositionY(float deltaTime)
	{
		float deltaY = targetY - getY();
		float stepValue = stepSpeed * deltaTime;
		if (slidingActive)
			stepValue *= (Math.abs(deltaY) / 30);
		if (deltaY < 0)
			stepValue = -stepValue;
		if (Math.abs(deltaY) > Math.abs(stepValue))
			super.setY(getY() + stepValue);
		else
			super.setY(targetY);
	}
	
	private void directPositionChangeY(float moveValue)
	{
		super.setY(getY() + moveValue);
	}

	@Override
	public void setX(float x)
	{
		if (x > getX())
			currentMoveInfo.setMoveDirection(Directions.RIGHT);
		else if (x < getX())
			currentMoveInfo.setMoveDirection(Directions.LEFT);
		targetX = x;
	}

	@Override
	public void setY(float y)
	{
		if (y > getY())
			currentMoveInfo.setMoveDirection(Directions.UP);
		else if (y < getY())
			currentMoveInfo.setMoveDirection(Directions.DOWN);
		targetY = y;
	}

	@Override
	public void setPosition(float x, float y)
	{
		setX(x);
		setY(y);
	}

	public MoveInfo getMoveInfo()
	{
		if (!slidingActive)
			return currentMoveInfo.withoutSliding();

		float deltaX = targetX - getX();
		float deltaY = targetY - getY();

		if (deltaX == 0 && deltaY == 0)
			return currentMoveInfo.reset();

		if (Math.abs(deltaX) > Math.abs(deltaY))
			updateCurrentMovementWithXAxis(deltaX);
		else
			updateCurrentMovementWithYAxis(deltaY);
		return currentMoveInfo;
	}

	private void updateCurrentMovementWithYAxis(float deltaY)
	{
		currentMoveInfo.setCurrentMovementSpeed(Math.abs(deltaY)/3.5f);
		int moveDirection = deltaY > 0 ? Directions.UP : Directions.DOWN;
		currentMoveInfo.setMoveDirection(moveDirection);
	}

	private void updateCurrentMovementWithXAxis(float deltaX)
	{
		currentMoveInfo.setCurrentMovementSpeed(Math.abs(deltaX)/3.5f);
		int moveDirection = deltaX > 0 ? Directions.RIGHT : Directions.LEFT;
		currentMoveInfo.setMoveDirection(moveDirection);
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
