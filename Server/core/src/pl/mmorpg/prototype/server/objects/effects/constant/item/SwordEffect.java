package pl.mmorpg.prototype.server.objects.effects.constant.item;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;
import pl.mmorpg.prototype.server.objects.effects.constant.ConstantEffect;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class SwordEffect extends ConstantEffect
{
	public SwordEffect(Monster target)
	{
		super(target);
	}

	@Override
	protected Statistics getDeltaModificationStatistics(Statistics baseStatistics)
	{
		return new Statistics.Builder()
				.attackPower(16)
				.attackRange(10)
				.build();
	}
}
