package pl.mmorpg.prototype.server.objects.effects.timed;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.Statistics;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class HpRegenerationEffect extends TimedEffectSelfUsedWithIntervals
{
	private int regenerationPower;
	private Monster target;
	private PacketsSender packetSender;

	public HpRegenerationEffect(float activeTime, float usageInterval, int regenerationPower, Monster target,
			PacketsSender packetSender)
	{
		super(activeTime, usageInterval);
		this.regenerationPower = regenerationPower;
		this.target = target;
		this.packetSender = packetSender;
	}

	@Override
	public void oneTimeUsage()
	{
		MonsterProperties targetProperties = target.getProperties();
		Statistics targetStatistics = target.getStatistics();
		if (targetProperties.hp + regenerationPower > targetStatistics.maxHp)
			targetProperties.hp = targetStatistics.maxHp;
		else
			targetProperties.hp += regenerationPower;
		packetSender.sendToAll(PacketsMaker.makeHpUpdatePacket(target.getId(), targetProperties.hp));
	}

}
