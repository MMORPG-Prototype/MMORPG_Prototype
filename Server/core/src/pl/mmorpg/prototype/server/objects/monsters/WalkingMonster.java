package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class WalkingMonster extends Monster
{
	private static final Random random = new Random();
	private int currentDirectionMoving = Directions.NONE;
	private final float minDirectionMovementChangeTime = 1.0f;
	private final float maxDirectionMovementChangeTime = 2.0f;
	private float currentDirectionMovementTime = 0.0f;
	private float randomizedDirectionTime = 0.0f;
	private CollisionMap collisionMap;
	private boolean isMovingRandomly = true;

	public WalkingMonster(Texture lookout, long id, MonsterProperties properties, CollisionMap collisionMap,
			PlayState playState)
	{
		super(lookout, id, playState, properties);
		this.collisionMap = collisionMap;
		randomizeDirectionOfMovementAndTime();
		setMoveSpeed(20.0f);
	}

	private float getRandomTime(float min, float max)
	{
		return random.nextFloat() * (max - min) + min;
	}

	@Override
	public void update(float deltaTime)
	{
		if (isMovingRandomly)
		{
			currentDirectionMovementTime += deltaTime;
			if (currentDirectionMovementTime >= randomizedDirectionTime)
				movementSwitch();
		}
		handleMovement(deltaTime);

		super.update(deltaTime);
	}

	private void movementSwitch()
	{
		if (currentDirectionMoving == Directions.NONE)
		{
			currentDirectionMovementTime = 0.0f;
			randomizeDirectionOfMovementAndTime();
		} else
		{
			currentDirectionMoving = Directions.NONE;
			randomizeTimeOfMovement();
		}
	}

	private void randomizeDirectionOfMovementAndTime()
	{
		currentDirectionMoving = random.nextInt(4) + 1;
		randomizeTimeOfMovement();
	}

	private void randomizeTimeOfMovement()
	{
		randomizedDirectionTime = getRandomTime(minDirectionMovementChangeTime, maxDirectionMovementChangeTime);
	}

	private void handleMovement(float deltaTime)
	{
		if (currentDirectionMoving == Directions.LEFT)
			moveLeft(collisionMap, deltaTime);
		else if (currentDirectionMoving == Directions.RIGHT)
			moveRight(collisionMap, deltaTime);
		else if (currentDirectionMoving == Directions.DOWN)
			moveDown(collisionMap, deltaTime);
		else if (currentDirectionMoving == Directions.UP)
			moveUp(collisionMap, deltaTime);
	}
	
	protected void setDirectionMoving(int direction)
	{
		this.currentDirectionMoving = direction;
	}

	protected void setMovingRandomly(boolean isMovingRandomly)
	{
		currentDirectionMoving = Directions.NONE;
		this.isMovingRandomly = isMovingRandomly;
	}

}
