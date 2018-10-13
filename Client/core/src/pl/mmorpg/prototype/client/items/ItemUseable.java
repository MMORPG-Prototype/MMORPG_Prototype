package pl.mmorpg.prototype.client.items;

import pl.mmorpg.prototype.client.communication.PacketsSender;
import pl.mmorpg.prototype.client.objects.monsters.Monster;

public interface ItemUseable
{
	void use(Monster target, PacketsSender packetSender);
	
	void useInterfaceUpdate();

	boolean shouldBeRemoved();
}
