package pl.mmorpg.prototype.server.objects.spawners;

import java.awt.Point;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.server.collision.interfaces.CollisionMap;
import pl.mmorpg.prototype.server.collision.pixelmap.IntegerRectangle;
import pl.mmorpg.prototype.server.objects.monsters.GameObjectsFactory;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.path.search.collisionDetectors.CollisionDetector;

public class MonsterSpawnerUnit
{
	private final Random random = new Random();

	private final Class<? extends Monster> monsterType;
	private final IntegerRectangle spawnArea;
	private final Rectangle walkingBounds;
	private final int maximumAmount;
	private final float spawnInterval;
	private final CollisionMap collisionMap;

	private float currentSpawnTime = 0.0f;
	private int currentAmount = 0;

	public MonsterSpawnerUnit(Class<? extends Monster> monsterType, IntegerRectangle spawnArea,
			Rectangle walkingBounds, int maximumAmount, float spawnInterval, CollisionMap collisionMap)
	{
		this.monsterType = monsterType;
		this.spawnArea = spawnArea;
		this.walkingBounds = walkingBounds;
		this.maximumAmount = maximumAmount;
		this.spawnInterval = spawnInterval;
		this.collisionMap = collisionMap;
	}

	public void updateSpawnInterval(float deltaTime)
	{
		currentSpawnTime += deltaTime;
	}

	public boolean shouldSpawnMonster()
	{
		if (currentSpawnTime >= spawnInterval && currentAmount < maximumAmount)
		{
			currentSpawnTime = 0.0f;
			return true;
		}
		return false;
	}

	public Monster getNewMonster(GameObjectsFactory factory, long id)
	{
		Monster monster = (Monster) factory.produce(monsterType, id, walkingBounds);
		Point randomSpawnLocation = getRandomSpawnLocation();
		monster.setPosition(randomSpawnLocation.x, randomSpawnLocation.y);
		currentAmount++;
		return monster;
	}

	public void decreaseSpawnedMonstersAmount()
	{
		currentAmount--;
	}

	private Point getRandomSpawnLocation()
	{
		Point randomPoint = getRandomPointInSpawnArea();
		while (!isValidRandomPoint(randomPoint))
			randomPoint = getRandomPointInSpawnArea();
		return randomPoint;
	}

	private boolean isValidRandomPoint(Point randomPoint)
	{
		return collisionMap.isValidPoint(randomPoint.x, randomPoint.y) &&
				collisionMap.getTopObject(randomPoint.x, randomPoint.y) == null;
	}

	private Point getRandomPointInSpawnArea()
	{
		int randomX = random.nextInt(spawnArea.width + 1) + spawnArea.x;
		int randomY = random.nextInt(spawnArea.height + 1) + spawnArea.y;

		return new Point(randomX, randomY);
	}

}
