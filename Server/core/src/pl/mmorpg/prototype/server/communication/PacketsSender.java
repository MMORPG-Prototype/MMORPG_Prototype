package pl.mmorpg.prototype.server.communication;

public interface PacketsSender
{
	void sendToAll(Object packet);
	
	void sendTo(int connectionId, Object packet);
}
