package pl.mmorpg.prototype.server.objects.monsters.spells;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.PlayerCharacter;
import pl.mmorpg.prototype.server.objects.monsters.Monster;
import pl.mmorpg.prototype.server.states.GameObjectsContainer;

public interface Spell
{
	int getDamage();
	
	int getNeededMana();
	
	void onUse(PlayerCharacter source, Monster target, PacketsSender packetsSender, GameObjectsContainer gameObjectsContainer);
}
