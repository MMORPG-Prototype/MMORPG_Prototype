package pl.mmorpg.prototype.server.objects.items;

import pl.mmorpg.prototype.server.communication.PacketsSender;
import pl.mmorpg.prototype.server.objects.monsters.Monster;

public interface Useable
{
	void use(Monster target, PacketsSender packetSender);
}
