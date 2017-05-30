package pl.mmorpg.prototype.server.objects.monsters.abilities;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public interface Ability
{
	boolean shouldUse();
	
	void use(Monster target, PacketsSender packetSender);
	
	boolean shouldBeUsedOnTargetedMonster();
	boolean shouldBeUsedOnItself();
}
