package pl.mmorpg.prototype.server.objects.spawners;

import java.awt.Point;
import java.util.Random;

import pl.mmorpg.prototype.server.collision.pixelmap.IntegerRectangle;
import pl.mmorpg.prototype.server.objects.monsters.GameObjectsFactory;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class MonsterSpawnerUnit
{
	private final static float DEFAULT_SPAWN_INTERVAL = 30.0f;
	private final Random random = new Random();

	private final Class<? extends Monster> monsterType;
	private final IntegerRectangle spawnArea;
	private final int maximumAmount;
	private final float spawnInterval;

	private float currentSpawnTime = 0.0f;
	private int currentAmount = 0;

	public MonsterSpawnerUnit(Class<? extends Monster> monsterType, IntegerRectangle spawnArea, int maximumAmount)
	{
		this(monsterType, spawnArea, maximumAmount, DEFAULT_SPAWN_INTERVAL);
	}

	public MonsterSpawnerUnit(Class<? extends Monster> monsterType, IntegerRectangle spawnArea, int maximumAmount,
			float spawnInterval)
	{
		this.monsterType = monsterType;
		this.spawnArea = spawnArea;
		this.maximumAmount = maximumAmount;
		this.spawnInterval = spawnInterval;
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
		Monster monster = (Monster) factory.produce(monsterType, id);
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
		int randomX = random.nextInt(spawnArea.width + 1) + spawnArea.x;
		int randomY = random.nextInt(spawnArea.height + 1) + spawnArea.y;
		return new Point(randomX, randomY);
	}

}
