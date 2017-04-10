package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.clientservercommon.monsterproperties.MonsterProperties;

public class DamageCalculator
{
	public static int getDamage(Monster source, Monster target)
	{
		MonsterProperties sourceProperties = source.getProperites();
		MonsterProperties targetProperties = target.getProperites();
		int damage = sourceProperties.attackPower - targetProperties.defense;
		if(damage <= 0)
			damage = 1;
		return damage;
	}
}
