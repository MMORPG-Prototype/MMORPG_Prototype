package pl.mmorpg.prototype.server.objects.monsters;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mmorpg.prototype.clientservercommon.packets.movement.Directions;
import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.MovableGameObject;
import pl.mmorpg.prototype.server.resources.Assets;

public abstract class Monster extends MovableGameObject
{
	private static final BitmapFont font = Assets.getFont();
	private static final Random random = new Random();
	private int currentDirectionMoving = Directions.NONE;
	private final float minDirectionMovementChangeTime = 1.0f;
	private final float maxDirectionMovementChangeTime = 2.0f;
	private float currentDirectionMovementTime = 0.0f;
	private float randomizedDirectionTime = 0.0f;
	private CollisionMap collisionMap;
	private final MonsterProperties properties;

	public Monster(Texture lookout, long id, CollisionMap collisionMap, PacketsSender packetsSender)
	{
		super(lookout, id, packetsSender);
		this.collisionMap = collisionMap;
		randomizeDirectionOfMovementAndTime();
		setMoveSpeed(20.0f);
		properties = getMonsterProperies();
		font.setColor(new Color(1,0,0,1));
	}

	private float getRandomTime(float min, float max)
	{
		return random.nextFloat() * (max - min) + min;
	}

	@Override
	public void update(float deltaTime)
	{
		currentDirectionMovementTime += deltaTime;
		if (currentDirectionMovementTime >= randomizedDirectionTime)
			movementSwitch();
		handleMovement();
		super.update(deltaTime);
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		font.draw(batch, String.valueOf(properties.hp), getX() + 3, getY() + 40);
	}

	private void movementSwitch()
	{
		if(currentDirectionMoving == Directions.NONE)
		{
			currentDirectionMovementTime = 0.0f;
			randomizeDirectionOfMovementAndTime();
		}
		else
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

	private void handleMovement()
	{
		if (currentDirectionMoving == Directions.LEFT)
			moveLeft(collisionMap);
		else if (currentDirectionMoving == Directions.RIGHT)
			moveRight(collisionMap);
		else if (currentDirectionMoving == Directions.DOWN)
			moveDown(collisionMap);
		else if (currentDirectionMoving == Directions.UP)
			moveUp(collisionMap);

	}
	
	public abstract MonsterProperties getMonsterProperies();

}
