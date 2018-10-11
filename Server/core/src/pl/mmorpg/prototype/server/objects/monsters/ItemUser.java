package pl.mmorpg.prototype.server.objects.monsters;

import pl.mmorpg.prototype.server.communication.PacketsSender;

public interface ItemUser extends ItemsOwner
{
	void useItem(long id, PacketsSender packetSender);

	void useItem(long id, Monster target, PacketsSender packetSender);
}