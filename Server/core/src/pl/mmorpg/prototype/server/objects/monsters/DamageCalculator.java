package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;

public class DamageCalculator
{
	public static int getDamage(Monster source, Monster target)
	{
		Statistics sourceStatistics = source.getStatistics();
		Statistics targetStatistics = target.getStatistics();
		int damage = sourceStatistics.attackPower - targetStatistics.defense;
		if(damage <= 0)
			return 1;
		return damage;
	}
}
