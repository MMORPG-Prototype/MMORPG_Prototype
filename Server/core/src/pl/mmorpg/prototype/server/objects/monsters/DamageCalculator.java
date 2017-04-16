package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.clientservercommon.packets.monsterproperties.MonsterProperties;

public class DamageCalculator
{
	public static int getDamage(Monster source, Monster target)
	{
		MonsterProperties sourceProperties = source.getProperties();
		MonsterProperties targetProperties = target.getProperties();
		int damage = sourceProperties.attackPower - targetProperties.defense;
		if(damage <= 0)
			damage = 1;
		return damage;
	}
}
