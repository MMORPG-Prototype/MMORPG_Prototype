package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.monsters.Monster;

@FunctionalInterface
public interface ItemUseable
{
	void use(Monster target, PacketsSender packetSender);
}
