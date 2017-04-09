package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.collision.CollisionMap;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.resources.Assets;

public class Dragon extends Monster
{
	
	public Dragon(long id, CollisionMap collisionMap, PacketsSender packetsSender)
	{
		super(Assets.get("monster.png"), id, collisionMap, packetsSender);
	}

	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	

	@Override
	public MonsterProperties getMonsterProperies()
	{
		return new MonsterProperties.Builder()
				.attackPower(10)
				.attackRange(30)
				.experienceGain(100)
				.maxHp(100)
				.maxMp(0)
				.build();
	}

}
