package pl.mmorpg.prototype.client.packethandlers;

public interface PacketHandler<T extends Object>
{
	void handle(T packet);

	boolean canBeHandled(T packet);
	
	boolean canBeOmmited(T packet);

	void tryHandlingUnhandledPackets();
}
