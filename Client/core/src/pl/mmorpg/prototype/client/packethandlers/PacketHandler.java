package pl.mmorpg.prototype.client.packethandlers;

public interface PacketHandler
{
	void handle(Object object);
	
	boolean canBeHandled(Object packet);
}
