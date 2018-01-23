package pl.mmorpg.prototype.client.objects;

import pl.mmorpg.prototype.client.packethandlers.NullPacketHandlerRegisterer;

public class NullPlayer extends Player
{
	public NullPlayer()
	{ 
		super(-1, null, new NullPacketHandlerRegisterer());
	}
}
