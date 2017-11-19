package pl.mmorpg.prototype.server.objects.monsters.abilities;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public class TimedHealAbility extends TimedAbility
{
	private int healPower;

	public TimedHealAbility(float timeInterval, int healPower)
	{
		super(timeInterval);
		this.healPower = healPower;
	}

	@Override
	public void use(Monster target, PacketsSender packetSender)
	{
		target.heal(healPower);
	}

	@Override
	public boolean shouldBeUsedOnTargetedMonster()
	{
		return false;
	}

	@Override
	public boolean shouldBeUsedOnItself()
	{
		return true;
	}
	

	
}
