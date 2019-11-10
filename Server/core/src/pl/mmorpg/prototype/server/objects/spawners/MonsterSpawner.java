package pl.mmorpg.prototype.server.objects.spawners;

import java.util.*;

import com.badlogic.gdx.math.Rectangle;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.GameObjectsFactory;

public class MonsterSpawner
{
	private final Map<Long, MonsterSpawnerUnit> spawnersKeyMonsterId = new HashMap<>();
	private final Collection<MonsterSpawnerUnit> spawners = new LinkedList<>();
	private final GameObjectsFactory factory;
	
	public MonsterSpawner(GameObjectsFactory factory)
	{
		this.factory = factory;
	}
	
	public void addSpawner(MonsterSpawnerUnit spawner)
	{
		spawners.add(spawner);
	}
	
	public void updateSpawners(float deltaTime)
	{
		spawners.forEach( s -> s.updateSpawnInterval(deltaTime));
	}

	public Optional<Monster> getNewMonster(long id)
	{
		Optional<MonsterSpawnerUnit> monsterSpawnerUnit = getSuiteSpawnerUnit();
		if (monsterSpawnerUnit.isEmpty())
			return Optional.empty();

		Monster monster = monsterSpawnerUnit.get().getNewMonster(factory, id);
		spawnersKeyMonsterId.put(monster.getId(), monsterSpawnerUnit.get());
		return Optional.of(monster);
	}

	private Optional<MonsterSpawnerUnit> getSuiteSpawnerUnit()
	{
		return spawners.stream()
				.filter(MonsterSpawnerUnit::shouldSpawnMonster)
				.findAny();
	}
	
	public void monsterHasDied(long monsterId)
	{
		boolean monsterWasSpawnedByThisSpawner = spawnersKeyMonsterId.containsKey(monsterId);
		if(monsterWasSpawnedByThisSpawner)
		{
			MonsterSpawnerUnit spawner = spawnersKeyMonsterId.remove(monsterId);
			spawner.decreaseSpawnedMonstersAmount();
		}
		
	}
	
}
