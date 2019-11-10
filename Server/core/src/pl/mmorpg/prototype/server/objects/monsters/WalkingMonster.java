package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.pixelmap.PixelCollisionMap;
import pl.mmorpg.prototype.server.objects.GameObject;
import pl.mmorpg.prototype.server.states.PlayState;

public abstract class WalkingMonster extends Monster
{
    private static final float minDirectionMovementChangeTime = 2.0f;
    private static final float maxDirectionMovementChangeTime = 8.0f;
    private static final Random random = new Random();
    private int currentDirectionMoving = Directions.NONE;
    private float currentDirectionMovementTime = 0.0f;
    private float randomizedDirectionTime = 0.0f;
    private final PixelCollisionMap<GameObject> collisionMap;
    private boolean isMovingRandomly = true;
    private final Rectangle walkingBounds;

    public WalkingMonster(Texture lookout, long id, MonsterProperties properties, Rectangle walkingBounds,
            PixelCollisionMap<GameObject> collisionMap, PlayState playState)
    {
        super(lookout, id, playState, properties);
        this.collisionMap = collisionMap;
        setMoveSpeed(20.0f);
        this.walkingBounds = walkingBounds;
        movementSwitch();
    }

    private float getRandomTime(float min, float max)
    {
        return (random.nextFloat() * (max - min)) + min;
    }

    @Override
    public void update(float deltaTime)
    {
        if (isMovingRandomly)
        {
            currentDirectionMovementTime += deltaTime;
            if (currentDirectionMovementTime >= randomizedDirectionTime)
            {
                movementSwitch();
                currentDirectionMovementTime = 0.0f;
            }
            handleMovement(deltaTime);
        }

        super.update(deltaTime);
    }

    private void movementSwitch()
    {
        currentDirectionMoving = getNextMovingDirection();
        randomizedDirectionTime = getRandomTime(minDirectionMovementChangeTime, maxDirectionMovementChangeTime);
    }

    private int getNextMovingDirection()
    {
        if (getX() <= walkingBounds.x)
            return Directions.RIGHT;
        else if (getX() >= walkingBounds.x + walkingBounds.width)
            return Directions.LEFT;
        else if (getY() >= walkingBounds.getY() + walkingBounds.getHeight())
            return Directions.DOWN;
        else if (getY() <= walkingBounds.getY())
            return Directions.UP;
        else if (currentDirectionMoving == Directions.NONE)
            return random.nextInt(4) + 1;
        return Directions.NONE;
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

    protected void setMovingRandomly(boolean isMovingRandomly)
    {
        this.isMovingRandomly = isMovingRandomly;
    }

}
