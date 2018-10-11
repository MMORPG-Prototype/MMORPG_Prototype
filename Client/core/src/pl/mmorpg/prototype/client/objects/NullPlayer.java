package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.collision.NullCollisionMap;
import pl.mmorpg.prototype.client.packethandlers.NullPacketHandlerRegisterer;
import pl.mmorpg.prototype.clientservercommon.packets.entities.UserCharacterDataPacket;

public class NullPlayer extends Player
{
	public NullPlayer()
	{ 
		super(-1, new NullCollisionMap(), new NullUserCharacterDataPacket(), new NullPacketHandlerRegisterer());
	}
}
