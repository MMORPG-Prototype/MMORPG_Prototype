package pl.mmorpg.prototype.server.objects.monsters.abilities;

import pl.mmorpg.prototype.clientservercommon.packets.monsters.properties.MonsterProperties;
import pl.mmorpg.prototype.server.communication.PacketsMaker;
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
        MonsterProperties targetProperties = target.getProperties();
        if(targetProperties.hp == targetProperties.maxHp)
        	return;
        int previous = targetProperties.hp;
        targetProperties.hp += healPower;
        if(targetProperties.hp > targetProperties.maxHp)
            targetProperties.hp = targetProperties.maxHp;
        int delta = targetProperties.hp - previous;
        
        packetSender.sendToAll(PacketsMaker.makeHpNotifiedIncreasePacket(delta, target.getId()));			
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
