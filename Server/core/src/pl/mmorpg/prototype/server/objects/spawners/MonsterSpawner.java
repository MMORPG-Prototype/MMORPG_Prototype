package pl.mmorpg.prototype.server.objects.spawners;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.objects.monsters.MonstersFactory;

public class MonsterSpawner
{
	private Map<Long, MonsterSpawnerUnit> spawnersKeyMonsterId = new HashMap<>();
	private Collection<MonsterSpawnerUnit> spawners = new LinkedList<>();
	private MonstersFactory factory;
	
	public MonsterSpawner(MonstersFactory factory)
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
	
	public Monster getNewMonster(long id)
	{
		MonsterSpawnerUnit monsterSpawnerUnit = getSuiteSpawnerUnit();
		if(monsterSpawnerUnit == null)
			return null;
		
		Monster monster = monsterSpawnerUnit.getNewMonster(factory, id);
		spawnersKeyMonsterId.put(monster.getId(), monsterSpawnerUnit);
		return monster;
	}

	private MonsterSpawnerUnit getSuiteSpawnerUnit()
	{
		return spawners.stream().filter(sp -> sp.shouldSpawnMonster()).findAny().orElse(null);
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
