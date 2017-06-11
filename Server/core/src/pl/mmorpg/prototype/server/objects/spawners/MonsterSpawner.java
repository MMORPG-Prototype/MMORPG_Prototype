package pl.mmorpg.prototype.server.objects.spawners;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

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
	
	public Monster getMonster(long id)
	{
		Optional<MonsterSpawnerUnit> suiteSpawner = spawners.stream()
			.filter( sp -> sp.shouldSpawnMonster())
			.findAny();
		if(!suiteSpawner.isPresent())
			return null;
		
		MonsterSpawnerUnit monsterSpawnerUnit = suiteSpawner.get();
		Monster monster = monsterSpawnerUnit.getNewMonster(factory, id);
		spawnersKeyMonsterId.put(monster.getId(), monsterSpawnerUnit);
		return monster;
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
