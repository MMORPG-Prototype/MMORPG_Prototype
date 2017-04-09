package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class Dragon extends WalkingMonster
{

	public Dragon(long id, CollisionMap collisionMap, PlayState playState)
	{
		super(Assets.get("monster.png"), id, getDragonProperies(), collisionMap, playState);
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	

	public static MonsterProperties getDragonProperies()
	{
		return new MonsterProperties.Builder()
				.attackPower(10)
				.attackRange(30)
				.defense(2)
				.experienceGain(100)
				.maxHp(100)
				.maxMp(0)
				.attackSpeed(2.0f)
				.build();
	}

}
