package pl.mmorpg.prototype.client.packethandlers;

public interface PacketHandlerRegisterer
{
	void register(@SuppressWarnings("rawtypes") PacketHandler packetHandler);

	void registerPrivateClassPacketHandlers(Object objectContainingDefinitionOfPrivatePacketHandlers);
	
	void unregister(@SuppressWarnings("rawtypes") PacketHandler packetHandler);
}
