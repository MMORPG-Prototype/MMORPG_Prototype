package pl.mmorpg.prototype.client.packethandlers;

public interface PacketHandlerRegisterer
{
	void register(PacketHandler packetHandler);

	void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers);

	void unregister(PacketHandler packetHandler);

	void unregister(PacketHandlerCategory category);
}
