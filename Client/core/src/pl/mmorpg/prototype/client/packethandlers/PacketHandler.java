package pl.mmorpg.prototype.client.packethandlers;

public interface PacketHandler<T extends Object>
{
	void handle(T packet);

	boolean canBeHandled(T packet);
	
	boolean canBeOmitted(T packet);

	void tryHandlingUnhandledPackets();

	default PacketHandlerCategory getCategory()
	{
		return PacketHandlerCategory.DEFAULT;
	}
}
